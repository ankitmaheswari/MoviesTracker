package com.ankitmaheswari.moviestracker.di

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import com.ankitmaheswari.moviestracker.data.storage.AppDatabase
import com.ankitmaheswari.moviestracker.data.storage.MoviesDao
import com.ankitmaheswari.moviestracker.data.storage.PlayListsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
@Module
class StorageModule {
    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "MoviesTracker.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(@NonNull database: AppDatabase): MoviesDao {
        return database.moviesDao()
    }

    @Provides
    @Singleton
    fun providePlayListsDao(@NonNull database: AppDatabase): PlayListsDao {
        return database.playListsDao()
    }
}