package com.example.filmes.ui.movieDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.movie.Movie
import com.example.filmes.repository.MoviesRepository
import com.example.filmes.utils.Movie2State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor( private val repository: MoviesRepository )
    : ViewModel() {

    private var _movieInfo = MutableLiveData<Movie>()
    var carregando: Boolean = true
    val movieInfo: LiveData<Movie> = _movieInfo

    private val _movieData = MutableStateFlow(Movie2State())
    val movieData: StateFlow<Movie2State> = _movieData


    fun getMovieInfo2( movieId: String ) {
        repository.getMovieInfo( movieId ).onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieData.value = Movie2State(isLoading = true)
                }
                is Resource.Error -> {
                    _movieData.value = Movie2State(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _movieData.value = Movie2State(data = it.data)
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    }