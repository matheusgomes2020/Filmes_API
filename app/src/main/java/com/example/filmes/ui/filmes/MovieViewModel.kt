package com.example.filmes.ui.filmes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.repository.MoviesRepository
import com.example.filmes.utils.MovieListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val moviesRepository: MoviesRepository )
    : ViewModel() {

    private val _movieListUpcoming = MutableStateFlow(MovieListState())
    private val _movieListNowPlaying = MutableStateFlow(MovieListState())
    private val _movieListRated = MutableStateFlow(MovieListState())
    private val _movieListPopular = MutableStateFlow(MovieListState())
    val movieListUpcoming: StateFlow<MovieListState> = _movieListUpcoming
    val movieListNowPlaying: StateFlow<MovieListState> = _movieListNowPlaying
    val movieListRated: StateFlow<MovieListState> = _movieListRated
    val movieListPopular: StateFlow<MovieListState> = _movieListPopular


    fun getUpcomingMovies() {
        moviesRepository.getUpcomingMovies().onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieListUpcoming.value = MovieListState(isLoading = true)
                }
                is Resource.Error -> {
                    _movieListUpcoming.value = MovieListState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _movieListUpcoming.value = MovieListState(data = it.data!!) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun getNowPlayingMovies() {
        moviesRepository.getNowPlayingMovies().onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieListNowPlaying.value = MovieListState(isLoading = true)
                }
                is Resource.Error -> {
                    _movieListNowPlaying.value = MovieListState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _movieListNowPlaying.value = MovieListState(data = it.data!!) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun getNowPopularMovies() {
        moviesRepository.getPopularMovies().onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieListPopular.value = MovieListState(isLoading = true)
                }
                is Resource.Error -> {
                    _movieListPopular.value = MovieListState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _movieListPopular.value = MovieListState(data = it.data!!) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun getNowRatedMovies() {
        moviesRepository.getRatedMovies().onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieListRated.value = MovieListState(isLoading = true)
                }
                is Resource.Error -> {
                    _movieListRated.value = MovieListState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _movieListRated.value = MovieListState(data = it.data!!) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

}