package com.ankitmaheswari.moviestracker.data.api

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
class LoggingInterceptor: HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Log.d("OkHttp", message)
    }
}