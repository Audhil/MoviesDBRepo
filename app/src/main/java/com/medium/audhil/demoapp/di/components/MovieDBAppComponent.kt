package com.medium.audhil.demoapp.di.components

import android.content.pm.PackageManager
import com.google.gson.Gson
import com.medium.audhil.demoapp.AppExecutors
import com.medium.audhil.demoapp.MovieDBAppDelegate
import com.medium.audhil.demoapp.data.local.db.AppDataBase
import com.medium.audhil.demoapp.data.remote.MovieDBAppAPIs
import com.medium.audhil.demoapp.di.modules.*
import com.medium.audhil.demoapp.ui.base.BaseActivity
import com.medium.audhil.demoapp.repository.base.BaseRepository
import com.medium.audhil.demoapp.ui.base.BaseViewModel
import dagger.Component
import javax.inject.Singleton

/*
 * Created by mohammed-2284 on 11/04/18.
 */

@Singleton
@Component(
        modules = [(ApplicationModule::class),
            (APIModule::class),
            (RepositoryModule::class),
            (DataBaseModule::class)]
)
interface MovieDBAppComponent {

    /*
    * access through injection of instances
    * */
    fun inject(into: MovieDBAppDelegate)

    fun inject(into: BaseViewModel)
    fun inject(into: BaseActivity)
    fun inject(into: BaseRepository)

    /*
    * direct access of instances
    * */
    fun getMovieDBAPIService(): MovieDBAppAPIs

    fun getPackageManager(): PackageManager
    fun getGSON(): Gson
    fun getMovieDBDataBase(): AppDataBase
    fun getExecutors(): AppExecutors
}