package com.ankitmaheswari.moviestracker.data.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
@Entity
data class PlayListData(@PrimaryKey(autoGenerate = true) val id:Int = 0, val name: String)
