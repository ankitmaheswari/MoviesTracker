package com.ankitmaheswari.moviestracker.data.api

import com.google.gson.annotations.SerializedName

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
data class MoviesResponse(
    @SerializedName("results")
    val moviesList: List<MovieResult>
)
