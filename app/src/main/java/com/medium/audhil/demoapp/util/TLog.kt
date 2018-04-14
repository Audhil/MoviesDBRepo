package com.medium.audhil.demoapp.util

import android.util.Log

/*
 * Created by mohammed-2284 on 11/04/18.
 */

object TLog {
    val DEBUG_BOOL = true

    fun v(tag: String, msg: String) {
        if (DEBUG_BOOL)
            Log.v(ConstantsUtil.DASH + tag, msg)
    }

    fun e(tag: String, msg: String) {
        if (DEBUG_BOOL)
            Log.e(ConstantsUtil.DASH + tag, msg)
    }

    fun d(tag: String, msg: String) {
        if (DEBUG_BOOL)
            Log.d(ConstantsUtil.DASH + tag, msg)
    }

    fun i(tag: String, msg: String) {
        if (DEBUG_BOOL)
            Log.i(ConstantsUtil.DASH + tag, msg)
    }

    fun w(tag: String, msg: String) {
        if (DEBUG_BOOL)
            Log.w(ConstantsUtil.DASH + tag, msg)
    }
}