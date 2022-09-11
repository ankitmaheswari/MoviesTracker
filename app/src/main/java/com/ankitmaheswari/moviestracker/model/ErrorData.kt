package com.ankitmaheswari.moviestracker.model

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
data class ErrorData(
    val status_code: Int,
    val status_message: String,
    val success: Boolean
)