package com.medium.audhil.demoapp.ui.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.medium.audhil.demoapp.MovieDBAppDelegate
import com.medium.audhil.demoapp.repository.HomeRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/*
 * Created by mohammed-2284 on 12/04/18.
 */

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var repo: HomeRepository

    init {
        MovieDBAppDelegate.INSTANCE.appDaggerComponent.inject(this)
    }

    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}