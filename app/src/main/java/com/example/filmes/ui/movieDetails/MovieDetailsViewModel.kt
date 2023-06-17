package com.example.filmes.ui.movieDetails

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.filmes.model.Movie
import com.example.filmes.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor( private val repository: MoviesRepository )
    : ViewModel() {

    //private val popularMoviesEmitter = MutableLiveData<Movie>()

    //val popularMovies: LiveData<Movie> = popularMoviesEmitter

    private val popularMoviesEmitter = MutableLiveData<List<Movie>>()
    private val ratedMoviesEmitter = MutableLiveData<List<Movie>>()

    val popularMovies: LiveData<List<Movie>> = popularMoviesEmitter

    init {
        getMovieInfo2()
        loadPopularMovies()

    }

    private fun loadPopularMovies() {

        viewModelScope.launch {

            popularMoviesEmitter.value = repository.getPopularMovies()

        }

    }

    suspend fun getMovieInfo( movieId: String ): Movie {

        return repository.getMovieInfo2( )

    }

    private fun getMovieInfo2( ) {

        viewModelScope.launch {

            //popularMoviesEmitter.value = repository.getMovieInfo2()

        }


    }


}


