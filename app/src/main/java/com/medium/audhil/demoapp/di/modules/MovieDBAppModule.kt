package com.medium.audhil.demoapp.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.medium.audhil.demoapp.MovieDBAppDelegate
import com.medium.audhil.demoapp.data.local.db.AppDataBase
import com.medium.audhil.demoapp.data.local.db.dao.MovieDao
import com.medium.audhil.demoapp.data.model.api.repomappers.HomeRepoMapper
import com.medium.audhil.demoapp.data.remote.MovieDBAppAPIEndPoints
import com.medium.audhil.demoapp.data.remote.MovieDBAppAPIs
import com.medium.audhil.demoapp.repository.HomeRepository
import com.medium.audhil.demoapp.util.TLog
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/*
 * Created by mohammed-2284 on 11/04/18.
 */

@Module
class ApplicationModule(private val application: MovieDBAppDelegate) {
    @Provides
    @Singleton
    internal fun giveContext(): Context {
        return this.application
    }

    @Provides
    @Singleton
    internal fun givePackageManager(): PackageManager {
        return application.packageManager
    }
}

//  API module
@Module(includes = [(ApplicationModule::class)])
class APIModule {
    val cacheSize: Long = 10 * 1024 * 1024
    val cacheTimeSec = 30

    //  interceptor - 1
    private val cacheInterceptor: Interceptor
        get() = Interceptor {
            val response = it.proceed(it.request())
            val cacheControl = CacheControl.Builder()
                    .maxAge(cacheTimeSec, TimeUnit.SECONDS)
                    .build()

            response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build()
        }


    //  interceptor - 4
    @Provides
    @Singleton
    fun giveLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("MovieDBAppRetrofit", message) })
        interceptor.level = if (TLog.DEBUG_BOOL)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    //  okHttpClient
    @Provides
    @Singleton
    fun giveOkHttpClient(
            context: Context,
            loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val cache = Cache(File(context.cacheDir, "http-cache"), cacheSize)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(cacheInterceptor)
                .addInterceptor(loggingInterceptor)
        httpClient.cache(cache)
        return httpClient.build()
    }

    //  retrofit instance
    @Provides
    @Singleton
    fun giveRetrofitAPIService(okHttpClient: OkHttpClient): MovieDBAppAPIs = Retrofit.Builder()
            .baseUrl(MovieDBAppAPIEndPoints.MOVIES_DB_API_END_POINT)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build().create(MovieDBAppAPIs::class.java)

    //  gson instance
    @Provides
    @Singleton
    fun giveGSONInstance(): Gson = Gson()
}


//  Database module
@Module
class DataBaseModule {
    @Provides
    @Singleton
    fun giveUseInMemoryDB(): Boolean = false    /*  make "useInMemory = true" in TestCases */

    @Provides
    @Singleton
    fun giveMovieDBAppDataBase(context: Context, useInMemory: Boolean): AppDataBase {
        val databaseBuilder = if (useInMemory) {
            Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
        } else {
            Room.databaseBuilder(context, AppDataBase::class.java, AppDataBase.dbName)
        }
        return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun giveMoviesDao(db: AppDataBase): MovieDao = db.moviesDao()
}


//  ItemMapper module(for converting data from one form to another)
@Module(includes = [(APIModule::class)])
class ItemMapperModule {
    @Provides
    @Singleton
    fun giveHomeRepoMapper(gson: Gson) = HomeRepoMapper(gson)
}


//  Repository module
@Module(includes = [(APIModule::class),
    (DataBaseModule::class),
    (ItemMapperModule::class)]
)
class RepositoryModule {
    @Provides
    @Singleton
    fun giveHomeRepo(): HomeRepository = HomeRepository()
}