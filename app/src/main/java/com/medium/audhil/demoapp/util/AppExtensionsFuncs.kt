package com.medium.audhil.demoapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import com.medium.audhil.demoapp.MovieDBAppDelegate

/*
 * Created by mohammed-2284 on 11/04/18.
 */

//  Toast display
fun Any.showToast(context: Context? = MovieDBAppDelegate.INSTANCE, duration: Int = Toast.LENGTH_SHORT) =
        when {
            this is String ->
                Toast.makeText(context, this, duration).show()
            this is Int ->
                Toast.makeText(context, this, duration).show()
            else ->
                Toast.makeText(context, ConstantsUtil.INVALID_INPUT, duration).show()
        }

//  showing Snackbar
fun View.showSnackBar() =
        Snackbar.make(this, "Implement anything you want!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

//  show logs
fun Any.showVLog(log: String) = TLog.v(this::class.java.simpleName, log)

fun Any.showELog(log: String) = TLog.e(this::class.java.simpleName, log)

fun Any.showDLog(log: String) = TLog.d(this::class.java.simpleName, log)

fun Any.showILog(log: String) = TLog.i(this::class.java.simpleName, log)

fun Any.showWLog(log: String) = TLog.w(this::class.java.simpleName, log)


//  internet connectivity bool
fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    return connectivityManager?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isConnected!! ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.isConnected!!
}

//  null checking
fun <T1, T2> Pair<T1?, T2?>.biLets(bothNotNull: (T1, T2) -> (Unit)) {
    if (this.first != null && this.second != null) {
        bothNotNull(this.first!!, this.second!!)
    }
}