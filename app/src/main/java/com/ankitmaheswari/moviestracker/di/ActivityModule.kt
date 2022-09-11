package com.ankitmaheswari.moviestracker.di

import com.ankitmaheswari.moviestracker.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity
}