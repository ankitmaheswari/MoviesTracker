package com.ankitmaheswari.moviestracker.data

import com.ankitmaheswari.moviestracker.data.api.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
interface MoviesService {

    @GET("3/movie/now_playing")
    suspend fun getMovies(@Query("page") pageNo: Int = 1): Response<MoviesResponse>
}