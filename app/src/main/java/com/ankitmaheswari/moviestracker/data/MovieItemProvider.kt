package com.ankitmaheswari.moviestracker.data

import com.ankitmaheswari.moviestracker.data.api.MovieResult
import com.ankitmaheswari.moviestracker.data.storage.Movie
import com.ankitmaheswari.moviestracker.data.storage.MoviesDao
import com.ankitmaheswari.moviestracker.data.storage.PlayList
import com.ankitmaheswari.moviestracker.data.storage.PlayListsDao
import com.ankitmaheswari.moviestracker.model.MovieItem
import com.ankitmaheswari.moviestracker.utils.MOVIE_IMAGE_BASE_PATH
import javax.inject.Inject

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
class MovieItemProvider @Inject constructor(private val moviesDao: MoviesDao,
                                            private val playListsDao: PlayListsDao) {

    fun getMovieItemsList(movieResultList: List<MovieResult>): List<MovieItem> {
        if (movieResultList.isEmpty()) {
            return listOf()
        }

        val movieItemList = mutableListOf<MovieItem>()

        val playListNameMap = getPlayListNameMap(playListsDao.getAllPlayLists()
            .map { PlayList(it.id, it.name) }
        )
        val playListsForMovieMap = getPlayListsForMovieMap(moviesDao.getAllMovies(), playListNameMap)

        for (movieResult in movieResultList) {
            movieItemList.add(prepareMovieItem(movieResult,
                playListsForMovieMap[movieResult.id] ?: listOf()))
        }

        return movieItemList
    }

    private fun getPlayListNameMap(playLists: List<PlayList>): Map<Int, String> {
        val playListNameMap = mutableMapOf<Int, String>()
        playLists.map {
            playListNameMap.put(it.id, it.name)
        }
        return playListNameMap
    }

    private fun getPlayListsForMovieMap(movieList: List<Movie>,
                                        platListNameMap: Map<Int, String>): Map<Int, List<String>> {
        val playListMovieIdMap = mutableMapOf<Int, MutableList<String>>()

        for (movie in movieList) {
            var playLists = mutableListOf<String>()
            if (playListMovieIdMap.containsKey(movie.movieId)) {
                playLists = playListMovieIdMap[movie.movieId]!!
            }
            playLists.add(platListNameMap[movie.playListId] ?: "")
            playListMovieIdMap[movie.movieId] = playLists
        }

        return playListMovieIdMap
    }

    private fun prepareMovieItem(movieResult: MovieResult, playLists: List<String>): MovieItem {
        return MovieItem(id = movieResult.id,
            title = movieResult.name,
            thumbnailUrl = "${MOVIE_IMAGE_BASE_PATH}${movieResult.thumbnailPath}",
            rating = movieResult.rating,
            playLists = playLists
        )
    }
}