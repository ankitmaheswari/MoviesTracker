package com.ankitmaheswari.moviestracker.data

import com.ankitmaheswari.moviestracker.data.api.ApiResponse
import com.ankitmaheswari.moviestracker.data.api.MoviesResponse
import com.ankitmaheswari.moviestracker.data.storage.*
import com.ankitmaheswari.moviestracker.model.MovieItem
import com.ankitmaheswari.moviestracker.model.Resource
import com.ankitmaheswari.moviestracker.utils.*
import java.io.IOException
import java.net.SocketException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
@Singleton
class MoviesRepository @Inject constructor(private val moviesService: MoviesService,
                                           private val moviesDao: MoviesDao,
                                           private val playListsDao: PlayListsDao,
                                           private val movieItemProvider: MovieItemProvider) {
    suspend fun getMovieItems(pageNo: Int = 1): Resource<List<MovieItem>> {
        var resource: Resource<List<MovieItem>> = Resource.error(msg = GENERIC_ERROR_MESSAGE, data = null)
        kotlin.runCatching {
            val moviesResponse = ApiResponse(moviesService.getMovies(pageNo))
            if (moviesResponse.isFailure) {
                resource = Resource.error(moviesResponse.message ?: GENERIC_ERROR_MESSAGE, null)
                return@runCatching
            }

            val movieResults = moviesResponse.body?.moviesList

            if (movieResults == null || movieResults.isEmpty()) {
                resource = Resource.error(
                    moviesResponse.message ?: EMPTY_MOVIE_LIST_ERROR_MESSAGE,
                    null
                )
                return@runCatching
            }

            val movieItemsList = movieItemProvider.getMovieItemsList(movieResults)

            resource = Resource.success(movieItemsList)
        }.onFailure {
            resource = if (it is IOException) {
                Resource.error(msg = ERROR_MESSAGE_NO_INTERNET, data = null)
            } else {
                Resource.error(msg = GENERIC_ERROR_MESSAGE, data = null)
            }
        }
        return resource
    }

    suspend fun addMovieToPlayList(movieItem: MovieItem, playList: PlayList): Resource<Any> {
        val moviesList = moviesDao.getMovieList(movieItem.id)
        for (movie in moviesList) {
            if (movie.playListId == playList.id) {
                return Resource.error(msg = ERROR_MESSAGE_MOVIE_ALREADY_TO_PLAYLIST, data = null)
            }
        }

        moviesDao.addMovieToPlayList(
            Movie(movieId = movieItem.id,
                playListId = playList.id,
                title = movieItem.title,
                rating = movieItem.rating,
                thumbnailUrl = movieItem.thumbnailUrl))
        return Resource.success(Any())
    }

    suspend fun addPlayList(name: String): Resource<Any> {
        if (playListsDao.getPlayList(name) != null) {
            return Resource.error(msg = ERROR_MESSAGE_PLAYLIST_ALREADY_EXISTS, data = null)
        }

        playListsDao.addPlayList(PlayListData(name = name))
        return Resource.success(Any())
    }

    suspend fun getPlayLists(): List<PlayList> {
        return playListsDao.getAllPlayLists().map { PlayList(it.id, it.name) }
    }

    suspend fun getMoviesForPlayList(playList: PlayList): List<MovieItem> {
        return moviesDao.getMoviesForPlaylist(playList.id)
            .map { MovieItem(
                it.movieId,
                it.title,
                it.thumbnailUrl,
                it.rating,
                listOf(playList.name)
            ) }
    }
}