package com.ankitmaheswari.moviestracker.data.api

import com.google.gson.annotations.SerializedName

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
data class MovieResult(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val name: String,
    @SerializedName("vote_average")
    val rating: Float,
    @SerializedName("poster_path")
    val thumbnailPath: String
)
