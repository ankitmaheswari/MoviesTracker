package com.ankitmaheswari.moviestracker

import com.ankitmaheswari.moviestracker.di.AppComponent
import com.ankitmaheswari.moviestracker.di.DaggerAppComponent
import dagger.android.DaggerApplication

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
class MoviesTrackerApplication : DaggerApplication() {

    private val appComponent: AppComponent = DaggerAppComponent.factory().create(this)

    override fun applicationInjector() = appComponent
}