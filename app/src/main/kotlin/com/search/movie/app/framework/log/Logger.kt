package com.search.movie.app.framework.log

import android.util.Log
import com.search.movie.app.BuildConfig
import kotlin.reflect.KClass

object Logger {
    private val TAG = "[search-movies]"


    fun i(loggedClass: KClass<out Any>, msg: String) {
        _log(Log.INFO, TAG, loggedClass.simpleName + ": " + msg)
    }

    fun d(loggedClass: KClass<out Any>, msg: String) {
        _log(Log.DEBUG, TAG, loggedClass.simpleName + ": " + msg)
    }

    fun w(loggedClass: KClass<out Any>, msg: String) {
        _log(Log.WARN, TAG, loggedClass.simpleName + ": " + msg)
    }

    fun e(loggedClass: KClass<out Any>, msg: String) {
        _log(Log.ERROR, TAG, loggedClass.simpleName + ": " + msg)
    }

    private fun _log(logPriority: Int, tag: String, msg: String) {
        if (!BuildConfig.DEBUG) return
        when (logPriority) {
            Log.VERBOSE -> Log.v(tag, msg)
            Log.DEBUG -> Log.d(tag, msg)
            Log.INFO -> Log.i(tag, msg)
            Log.WARN -> Log.w(tag, msg)
            Log.ERROR -> Log.e(tag, msg)
        }
    }

}