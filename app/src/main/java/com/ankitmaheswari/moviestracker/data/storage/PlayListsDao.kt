package com.ankitmaheswari.moviestracker.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
@Dao
interface PlayListsDao {
    @Insert
    fun addPlayList(playList: PlayListData)

    @Query("SELECT * FROM PlayListData ORDER BY id")
    fun getAllPlayLists(): List<PlayListData>

    @Query("SELECT * FROM PlayListData WHERE name = :name")
    fun getPlayList(name: String): PlayListData?
}