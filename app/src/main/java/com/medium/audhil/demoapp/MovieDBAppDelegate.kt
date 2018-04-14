package com.medium.audhil.demoapp

import android.app.Application
import com.medium.audhil.demoapp.di.components.DaggerMovieDBAppComponent
import com.medium.audhil.demoapp.di.components.MovieDBAppComponent
import com.medium.audhil.demoapp.di.modules.APIModule
import com.medium.audhil.demoapp.di.modules.ApplicationModule
import com.medium.audhil.demoapp.di.modules.RepositoryModule

/*
 * Created by mohammed-2284 on 11/04/18.
 */

class MovieDBAppDelegate : Application() {

    lateinit var appDaggerComponent: MovieDBAppComponent
        private set

    companion object {
        lateinit var INSTANCE: MovieDBAppDelegate
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appDaggerComponent = DaggerMovieDBAppComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .aPIModule(APIModule())
                .repositoryModule(RepositoryModule())
                .build()
        appDaggerComponent.inject(this)
    }
}