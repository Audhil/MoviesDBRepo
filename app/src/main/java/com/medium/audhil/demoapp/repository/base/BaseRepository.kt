package com.medium.audhil.demoapp.repository.base

import com.google.gson.Gson
import com.medium.audhil.demoapp.AppExecutors
import com.medium.audhil.demoapp.MovieDBAppDelegate
import com.medium.audhil.demoapp.data.local.db.dao.MovieDao
import com.medium.audhil.demoapp.data.model.api.generic.ErrorLiveData
import com.medium.audhil.demoapp.data.model.api.generic.NetworkError
import com.medium.audhil.demoapp.data.model.api.repomappers.HomeRepoMapper
import com.medium.audhil.demoapp.data.remote.MovieDBAppAPIs
import com.medium.audhil.demoapp.rx.IRxListeners
import com.medium.audhil.demoapp.util.showVLog
import javax.inject.Inject

/*
 * Created by mohammed-2284 on 11/04/18.
 */

abstract class BaseRepository : IRxListeners<Any> {

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var api: MovieDBAppAPIs

    @Inject
    lateinit var moviesDao: MovieDao

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var mapper: HomeRepoMapper

    init {
        MovieDBAppDelegate.INSTANCE.appDaggerComponent.inject(this)
    }

    @Inject
    lateinit var errorLiveData: ErrorLiveData

    override fun onSuccess(obj: Any?, tag: String) = showVLog("onSuccess() :: " + tag)

    override fun onSocketTimeOutException(t: Throwable?, tag: String) {
        showVLog("onSocketTimeOutException :: + tag " + tag + " :: t?.message :: " + t?.message)
        errorLiveData.setNetworkError(NetworkError.SOCKET_TIMEOUT)
    }

    override fun onUnknownHostException(t: Throwable?, tag: String) {
        showVLog("onUnknownHostException :: + tag " + tag + " :: t?.message :: " + t?.message)
        errorLiveData.setNetworkError(NetworkError.DISCONNECTED)
    }

    override fun onIllegalArgumentException(t: Throwable?, tag: String) {
        showVLog("onIllegalArgumentException :: + tag " + tag + " :: t?.message :: " + t?.message)
        errorLiveData.setNetworkError(NetworkError.BAD_URL)
    }

    override fun onUnKnownException(t: Throwable?, tag: String) {
        showVLog("onUnKnownException :: + tag " + tag + " :: t?.message :: " + t?.message)
        errorLiveData.setNetworkError(NetworkError.UNKNOWN)
    }

    override fun onComplete(tag: String) {
        showVLog("onComplete() :: " + tag)
    }
}