package com.ankitmaheswari.moviestracker.di

import androidx.annotation.NonNull
import com.ankitmaheswari.moviestracker.data.MoviesService
import com.ankitmaheswari.moviestracker.data.api.LoggingInterceptor
import com.ankitmaheswari.moviestracker.data.api.RequestInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .addInterceptor(HttpLoggingInterceptor(LoggingInterceptor())
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesService(retrofit: Retrofit): MoviesService {
        return retrofit.create(MoviesService::class.java)
    }
}