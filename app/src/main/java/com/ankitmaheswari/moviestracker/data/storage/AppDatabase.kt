package com.ankitmaheswari.moviestracker.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
@Database(entities = [Movie::class, PlayListData::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun playListsDao(): PlayListsDao
}