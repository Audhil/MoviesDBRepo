package com.medium.audhil.demoapp.ui.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.medium.audhil.demoapp.MovieDBAppDelegate
import com.medium.audhil.demoapp.util.*
import com.medium.audhil.demoapp.data.model.ui.IActivityToFragment

/*
 * Created by mohammed-2284 on 11/04/18.
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MovieDBAppDelegate.INSTANCE.appDaggerComponent.inject(this)
    }

    //  Activity to Fragment Interface
    var iActivityFragment: IActivityToFragment? = null

    //  bool
    var isInternetUnAvailableBool = false

    //  internet listener
    private var internetListener: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (this@BaseActivity.isNetworkConnected() && isInternetUnAvailableBool) {
                iActivityFragment?.internetAvailable()
                isInternetUnAvailableBool = false
            } else {
                if (!this@BaseActivity.isNetworkConnected()) {
                    iActivityFragment?.internetUnAvailable()
                    isInternetUnAvailableBool = true
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(internetListener, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(internetListener)
    }
}
