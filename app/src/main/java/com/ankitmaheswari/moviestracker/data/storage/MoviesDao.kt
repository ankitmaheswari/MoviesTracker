package com.ankitmaheswari.moviestracker.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMovieToPlayList(movie: Movie)

    @Query(" SELECT * FROM Movie GROUP BY movieId")
    fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM Movie WHERE movieId = :id")
    fun getMovieList(id: Int): List<Movie>

    @Query("SELECT * FROM Movie WHERE playListId = :playListId")
    fun getMoviesForPlaylist(playListId: Int): List<Movie>
}