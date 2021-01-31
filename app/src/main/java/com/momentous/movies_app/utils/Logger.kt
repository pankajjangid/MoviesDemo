package com.momentous.movies_app.utils

import android.util.Log
import com.momentous.movies_app.BuildConfig

class Logger(name: String?) {


    private val TAG = name ?: Logger::class.java.simpleName

    companion object {

        fun print(msg: String) {
            if (BuildConfig.SHOW_LOGS)
                kotlin.io.print(msg)
            else
                kotlin.io.print("LIVE MODE")

        }

        fun debug(TAG: String, msg: String) {
            if (BuildConfig.SHOW_LOGS)
                Log.d(TAG, msg)
            else
                kotlin.io.print("LIVE MODE")

        }

        fun debug(msg: String) {
            if (BuildConfig.SHOW_LOGS)
                Log.d("Logger", msg)
            else
                kotlin.io.print("LIVE MODE")


        }

    }

    fun debug(msg: String) {
        if (BuildConfig.SHOW_LOGS)
            Log.d(TAG, msg)
        else
            kotlin.io.print("LIVE MODE")


    }
}