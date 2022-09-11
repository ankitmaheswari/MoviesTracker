package com.ankitmaheswari.moviestracker.data.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
@Entity
data class Movie(@PrimaryKey(autoGenerate = true) val _id: Int=0,
                 val movieId:Int,
                 val title:String,
                 val thumbnailUrl: String,
                 val rating: Float,
                 val playListId: Int)
