package com.medium.audhil.demoapp.rx

import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.subscribers.DisposableSubscriber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/*
 * Created by mohammed-2284 on 11/04/18.
 */

//  for Flowable
class AppRxDisposableSubscriber<T>(private val iListener: IRxListeners<T>,
                                   val tag: String) : DisposableSubscriber<T>() {

    override fun onComplete() = iListener.onComplete(tag)

    override fun onError(e: Throwable?) {
        e?.let {
            when (it) {
                is UnknownHostException ->
                    iListener.onUnknownHostException(e, tag)

                is IllegalArgumentException ->
                    iListener.onIllegalArgumentException(e, tag)

                is SocketTimeoutException ->
                    iListener.onSocketTimeOutException(e, tag)

                else ->
                    iListener.onUnKnownException(e, tag)
            }
        }
    }

    override fun onNext(t: T) = iListener.onSuccess(t, tag)
}

//  for Observerable
class AppRxDisposableObserver<T>(private val iListener: IRxListeners<T>,
                                 val tag: String) : DisposableObserver<T>() {
    override fun onComplete() = iListener.onComplete(tag)

    override fun onNext(t: T) = iListener.onSuccess(t, tag)

    override fun onError(e: Throwable) {
        e.let {
            when (it) {
                is UnknownHostException ->
                    iListener.onUnknownHostException(e, tag)

                is IllegalArgumentException ->
                    iListener.onIllegalArgumentException(e, tag)

                is SocketTimeoutException ->
                    iListener.onSocketTimeOutException(e, tag)

                else ->
                    iListener.onUnKnownException(e, tag)
            }
        }
    }
}

//  for Single
class AppRxDisposableSingleObserver<T>(private val iListener: IRxListeners<T>,
                                       val tag: String) : DisposableSingleObserver<T>() {

    override fun onError(e: Throwable) {
        e.let {
            when (it) {
                is UnknownHostException ->
                    iListener.onUnknownHostException(e, tag)

                is IllegalArgumentException ->
                    iListener.onIllegalArgumentException(e, tag)

                is SocketTimeoutException ->
                    iListener.onSocketTimeOutException(e, tag)

                else ->
                    iListener.onUnKnownException(e, tag)
            }
        }
    }

    override fun onSuccess(t: T) = iListener.onSuccess(t, tag)
}