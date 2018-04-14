package com.medium.audhil.demoapp.ui.home

import android.app.Application
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import com.medium.audhil.demoapp.data.model.api.generic.NetworkError
import com.medium.audhil.demoapp.data.model.db.MoviesEntity
import com.medium.audhil.demoapp.ui.base.BaseViewModel

/*
 * Created by mohammed-2284 on 12/04/18.
 */

class HomeViewModel(application: Application) : BaseViewModel(application) {

    //  Visibility kinda stuffs
    var fullPageVisibility = ObservableField<Boolean>(true)

    private var page: Int = 1

    var noMoreDataLiveDataBool: MutableLiveData<Boolean> = MutableLiveData()

    init {
        repo.noMoreDataListener = {
            noMoreDataLiveDataBool.value = it
        }
    }

    //  error live data
    var errorLiveData = MediatorLiveData<NetworkError>().apply {
        addSource(repo.errorLiveData) {
            value = it
        }
    }

    //  reddit posts
    var moviesListLiveData = MediatorLiveData<List<MoviesEntity>>().apply {
        addSource(repo.getMoviesFromDB()) {
            value = it
        }
    }

    //  fetching time sheets
    fun fetchMovies() {
        compositeDisposable.add(repo.fetchMovies(page))
        page += 1
    }

    //  refreshing
    fun refresh() {
        page = 1
        repo.deleteTableRows()
    }
}