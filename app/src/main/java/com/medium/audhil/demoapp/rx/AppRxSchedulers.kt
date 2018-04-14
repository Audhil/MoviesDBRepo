package com.medium.audhil.demoapp.rx

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/*
 * Created by mohammed-2284 on 11/04/18.
 */

@Suppress("UNCHECKED_CAST")
object AppRxSchedulers {

    //  Observable transformer
    private var transformer: ObservableTransformer<Any, Any> = ObservableTransformer { upstream ->
        upstream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    internal fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return transformer as ObservableTransformer<T, T>
    }

    //  Flowable transformer
    private var flowableTransformer: FlowableTransformer<Any, Any> = FlowableTransformer { upstream ->
        upstream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    internal fun <T> applyFlowableSchedulers(): FlowableTransformer<T, T> {
        return flowableTransformer as FlowableTransformer<T, T>
    }

    //  Single transformer
    private var singleTransformer: SingleTransformer<Any, Any> = SingleTransformer { upstream ->
        upstream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    internal fun <T> applySingleSchedulers(): SingleTransformer<T, T> {
        return singleTransformer as SingleTransformer<T, T>
    }
}