package com.medium.audhil.demoapp.ui.base

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medium.audhil.demoapp.data.model.ui.IActivityToFragment

/*
 * Created by mohammed-2284 on 11/04/18.
 */

abstract class BaseLifeCycleFragment<B : ViewDataBinding, T : AndroidViewModel> : Fragment() {

    abstract val viewModelClass: Class<T>

    protected val viewModel: T by lazy {
        ViewModelProviders.of(this).get(viewModelClass)
    }

    lateinit var viewDataBinding: B

    var rootView: View? = null

    var iActivityToFragment = object : IActivityToFragment {

        override fun internetAvailable() = this@BaseLifeCycleFragment.internetAvailable()

        override fun internetUnAvailable() = this@BaseLifeCycleFragment.internetUnAvailable()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as BaseActivity).iActivityFragment = iActivityToFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate<B>(inflater, getLayoutId(), container, false)
        rootView = viewDataBinding.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()
        initAPIErrorLiveData()
    }

    //  internet callbacks
    abstract fun internetAvailable()

    abstract fun internetUnAvailable()

    //  API error live data
    abstract fun initAPIErrorLiveData()

    //  data binding variable
    abstract fun getBindingVariable(): Int

    //  layout id
    @LayoutRes
    abstract fun getLayoutId(): Int
}