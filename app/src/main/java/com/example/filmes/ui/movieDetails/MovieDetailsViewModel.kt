package com.example.filmes.ui.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.repository.MoviesRepository
import com.example.filmes.utils.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor( private val repository: MoviesRepository )
    : ViewModel() {

    private val _movieData = MutableStateFlow(MovieState())
    val movieData: StateFlow<MovieState> = _movieData

    fun getMovieInfo(movieId: String ) {
        repository.getMovieInfo( movieId ).onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieData.value = MovieState(isLoading = true)
                }
                is Resource.Error -> {
                    _movieData.value = MovieState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _movieData.value = MovieState(data = it.data)
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    }