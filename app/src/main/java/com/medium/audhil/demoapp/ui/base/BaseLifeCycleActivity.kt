package com.medium.audhil.demoapp.ui.base

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes

/*
 * Created by mohammed-2284 on 11/04/18.
 */

abstract class BaseLifeCycleActivity<B : ViewDataBinding, T : AndroidViewModel> : BaseActivity() {

    abstract val viewModelClass: Class<T>

    protected val viewModel: T by lazy { ViewModelProviders.of(this).get(viewModelClass) }

    lateinit var viewDataBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()
    }

    //  data binding variable
    abstract fun getBindingVariable(): Int

    //  layout id
    @LayoutRes
    abstract fun getLayoutId(): Int
}