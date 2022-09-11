package com.ankitmaheswari.moviestracker.view

import com.ankitmaheswari.moviestracker.model.MovieItem

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
interface MovieItemClickListener {

    fun onItemClick(movieItem: MovieItem)
}