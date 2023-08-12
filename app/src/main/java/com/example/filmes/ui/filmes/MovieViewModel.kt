package com.example.filmes.ui.filmes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.movie.Movie
import com.example.filmes.repository.MoviesRepository
import com.example.filmes.utils.MovieList2State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val moviesRepository: MoviesRepository )
    : ViewModel() {

    private val _movieListUpcoming = MutableStateFlow(MovieList2State())
    private val _movieListNowPlaying = MutableStateFlow(MovieList2State())
    private val _movieListRated = MutableStateFlow(MovieList2State())
    private val _movieListPopular = MutableStateFlow(MovieList2State())
    val movieListUpcoming: StateFlow<MovieList2State> = _movieListUpcoming
    val movieListNowPlaying: StateFlow<MovieList2State> = _movieListNowPlaying
    val movieListRated: StateFlow<MovieList2State> = _movieListRated
    val movieListPopular: StateFlow<MovieList2State> = _movieListPopular


    fun getUpcomingMovies() {
        moviesRepository.getUpcomingMovies().onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieListUpcoming.value = MovieList2State(isLoading = true)
                }
                is Resource.Error -> {
                    _movieListUpcoming.value = MovieList2State(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _movieListUpcoming.value = MovieList2State(data = it.data!!) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun getNowPlayingMovies() {
        moviesRepository.getNowPlayingMovies().onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieListNowPlaying.value = MovieList2State(isLoading = true)
                }
                is Resource.Error -> {
                    _movieListNowPlaying.value = MovieList2State(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _movieListNowPlaying.value = MovieList2State(data = it.data!!) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun getNowPopularMovies() {
        moviesRepository.getPopularMovies().onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieListPopular.value = MovieList2State(isLoading = true)
                }
                is Resource.Error -> {
                    _movieListPopular.value = MovieList2State(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _movieListPopular.value = MovieList2State(data = it.data!!) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun getNowRatedMovies() {
        moviesRepository.getRatedMovies().onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieListRated.value = MovieList2State(isLoading = true)
                }
                is Resource.Error -> {
                    _movieListRated.value = MovieList2State(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _movieListRated.value = MovieList2State(data = it.data!!) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

}