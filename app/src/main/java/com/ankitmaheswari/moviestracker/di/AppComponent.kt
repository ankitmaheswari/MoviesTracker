package com.ankitmaheswari.moviestracker.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    NetworkModule::class,
    StorageModule::class,
    ViewModelModule::class,
    ActivityModule::class
])
interface AppComponent: AndroidInjector<DaggerApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}