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
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor( private val repository: MoviesRepository )
    : ViewModel() {

    private var movieInfoEmitter = MutableLiveData<Movie>()

    val movieInfo: LiveData<Movie> = movieInfoEmitter

    private val popularMoviesEmitter = MutableLiveData<List<Movie>>()
    private val ratedMoviesEmitter = MutableLiveData<List<Movie>>()

    val popularMovies: LiveData<List<Movie>> = popularMoviesEmitter

    init {
        //getMovieInfo("667538")
        //getMovieInfo2()

    }



     fun getMovieInfo( movieId: String ) {

        try {
            viewModelScope.launch {
                movieInfoEmitter.value = repository.getMovieInfo(movieId)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }



    }


     /*
    private fun getMovieInfo2( ) {

        viewModelScope.launch {

            movieInfoEmitter.value = repository.getMovieInfo2()

        }


      */

    }




//}


