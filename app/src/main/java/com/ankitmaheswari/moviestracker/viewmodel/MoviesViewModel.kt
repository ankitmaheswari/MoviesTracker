package com.ankitmaheswari.moviestracker.viewmodel

import androidx.lifecycle.*
import com.ankitmaheswari.moviestracker.data.MoviesRepository
import com.ankitmaheswari.moviestracker.data.storage.PlayList
import com.ankitmaheswari.moviestracker.model.MovieItem
import com.ankitmaheswari.moviestracker.model.Resource
import com.ankitmaheswari.moviestracker.model.ResourceStatus
import com.ankitmaheswari.moviestracker.utils.MESSAGE_EMPTY_PLAYLIST
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository): ViewModel() {

    private val _movieItemsLiveData: MutableLiveData<List<MovieItem>> = MutableLiveData()
    private val _errorLiveData: MutableLiveData<String> = MutableLiveData()
    private val _loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getMovieItems() {
        viewModelScope.launch {
            _loadingLiveData.postValue(true)
            val movieItemsResource = fetchMovieItems()
            with(movieItemsResource) {
                if (resourceStatus == ResourceStatus.ERROR) {
                    _errorLiveData.postValue(message)
                } else if (resourceStatus == ResourceStatus.SUCCESS) {
                    _movieItemsLiveData.postValue(data)
                }
            }
            _loadingLiveData.postValue(false)
        }
    }

    fun getPlayLists(): LiveData<List<PlayList>> {

        val playListLiveDate = MutableLiveData<List<PlayList>>()

        viewModelScope.launch {
            _loadingLiveData.postValue(true)
            val playLists = withContext(Dispatchers.Default) {
                moviesRepository.getPlayLists()
            }
            playListLiveDate.postValue(playLists)
            _loadingLiveData.postValue(false)
        }
        return playListLiveDate
    }

    fun addMovieToPlayList(movieItem: MovieItem, playList: PlayList): LiveData<Boolean> {
        val successLiveData = MutableLiveData<Boolean>()
        viewModelScope.launch {
            _loadingLiveData.postValue(true)
            val addPlayListResource  = withContext(Dispatchers.Default) {
                moviesRepository.addMovieToPlayList(movieItem, playList)
            }
            with(addPlayListResource) {
                if (resourceStatus == ResourceStatus.ERROR) {
                    _errorLiveData.postValue(message)
                    successLiveData.postValue(false)
                } else if (resourceStatus == ResourceStatus.SUCCESS) {
                    successLiveData.postValue(true)
                }
            }
            _loadingLiveData.postValue(false)
        }
        return successLiveData
    }

    fun addPlayList(name: String): LiveData<Boolean> {
        val successLiveData = MutableLiveData<Boolean>()
        viewModelScope.launch {
            _loadingLiveData.postValue(true)
            val addPlayListResource  = withContext(Dispatchers.Default) {
                moviesRepository.addPlayList(name)
            }
            with(addPlayListResource) {
                if (resourceStatus == ResourceStatus.ERROR) {
                    _errorLiveData.postValue(message)
                    successLiveData.postValue(false)
                } else if (resourceStatus == ResourceStatus.SUCCESS) {
                    successLiveData.postValue(true)
                }
            }
            _loadingLiveData.postValue(false)
        }
        return successLiveData
    }

    fun getMovieItemsLiveData(): LiveData<List<MovieItem>> {
        return _movieItemsLiveData
    }

    fun getErrorLiveData(): LiveData<String> {
        return _errorLiveData
    }

    fun getLoadingLiveData(): LiveData<Boolean> {
        return _loadingLiveData
    }

    fun filterMovies(playList: PlayList) {
        viewModelScope.launch {
            _loadingLiveData.postValue(true)
            val movies = withContext(Dispatchers.Default) {
                moviesRepository.getMoviesForPlayList(playList)
            }
            if (movies.isEmpty()) {
                _errorLiveData.postValue(MESSAGE_EMPTY_PLAYLIST)
            } else {
                _movieItemsLiveData.postValue(movies)
            }

            _loadingLiveData.postValue(false)
        }
    }

    private suspend fun fetchMovieItems(): Resource<List<MovieItem>> = withContext(Dispatchers.Default) {
        moviesRepository.getMovieItems()
    }
}