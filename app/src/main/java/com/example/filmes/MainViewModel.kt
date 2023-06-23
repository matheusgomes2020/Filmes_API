package com.example.filmes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.model.Movie
import com.example.filmes.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor( private val moviesRepository: MoviesRepository )
    : ViewModel() {


    private val popularMoviesEmitter = MutableLiveData<List<Movie>>()
    private val ratedMoviesEmitter = MutableLiveData<List<Movie>>()
    private val upcomingMoviesEmitter = MutableLiveData<List<Movie>>()

    val popularMovies: LiveData<List<Movie>> = popularMoviesEmitter
    val ratedMovies: LiveData<List<Movie>> = ratedMoviesEmitter
    val upcomingMovies: LiveData<List<Movie>> = upcomingMoviesEmitter


    init {

        loadPopularMovies()
        loadRatedMovies()
        loadUpcoming()

    }

    private fun loadUpcoming() {

        viewModelScope.launch {

            upcomingMoviesEmitter.value = moviesRepository.getUpcoming()

        }

    }

    private fun loadPopularMovies() {

        viewModelScope.launch {

            popularMoviesEmitter.value = moviesRepository.getPopularMovies()

        }

    }

    private fun loadRatedMovies() {

        viewModelScope.launch {

            ratedMoviesEmitter.value = moviesRepository.getRatedMovies()

        }

    }

}