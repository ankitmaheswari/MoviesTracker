package com.ankitmaheswari.moviestracker.model

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
data class MovieItem(
    val id: Int,
    val title: String,
    val thumbnailUrl: String,
    val rating: Float,
    val playLists: List<String> = listOf()
)
