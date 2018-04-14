package com.medium.audhil.demoapp.rx

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

/*
 * Created by mohammed-2284 on 11/04/18.
 */

//  Flowable
fun <T> Flowable<T>.makeFlowableRxConnection(iRxListeners: IRxListeners<T>, tag: String): Disposable =
        this.compose(AppRxSchedulers.applyFlowableSchedulers())
                .subscribeWith(AppRxDisposableSubscriber<T>(iRxListeners, tag))

//  Observable
fun <T> Observable<T>.makeObservableRxConnection(iRxListeners: IRxListeners<T>, tag: String): Disposable =
        this.compose(AppRxSchedulers.applySchedulers())
                .subscribeWith(AppRxDisposableObserver<T>(iRxListeners, tag))

//  Single
fun <T> Single<T>.makeSingleRxConnection(iRxListeners: IRxListeners<T>, tag: String): Disposable =
        this.compose(AppRxSchedulers.applySingleSchedulers())
                .subscribeWith(AppRxDisposableSingleObserver<T>(iRxListeners, tag))