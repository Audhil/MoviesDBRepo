package com.medium.audhil.demoapp.rx

/*
 * Created by mohammed-2284 on 11/04/18.
 */

interface IRxListeners<in T> {
    fun onSuccess(obj: T?, tag: String)
    fun onUnknownHostException(t: Throwable?, tag: String)
    fun onIllegalArgumentException(t: Throwable?, tag: String)
    fun onUnKnownException(t: Throwable?, tag: String)
    fun onSocketTimeOutException(t: Throwable?, tag: String)
    fun onComplete(tag: String)
}