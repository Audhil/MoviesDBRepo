package com.medium.audhil.demoapp.data.model.api.generic

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.medium.audhil.demoapp.AppExecutors
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

/*
 * Created by mohammed-2284 on 11/04/18.
 */

class ErrorLiveData
@Inject
constructor(private val executor: AppExecutors) : LiveData<NetworkError>() {

    private val pending = AtomicBoolean(false)

    fun setNetworkError(value: NetworkError) {
        executor.mainThread().execute {
            pending.set(true)
            super.setValue(value)
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<NetworkError>) {
        super.observe(owner, Observer<NetworkError> { value ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(value)
            }
        })
    }
}