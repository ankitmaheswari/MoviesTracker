package com.ankitmaheswari.moviestracker.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ankitmaheswari.moviestracker.viewmodel.MoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    internal abstract fun bindMoviesViewModel(moviesViewModel: MoviesViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}