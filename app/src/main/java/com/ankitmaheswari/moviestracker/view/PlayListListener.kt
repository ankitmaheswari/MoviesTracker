package com.ankitmaheswari.moviestracker.view

import com.ankitmaheswari.moviestracker.data.storage.PlayList

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
interface PlayListListener {
    fun onItemClick(playList: PlayList)

    fun addPlayList()
}