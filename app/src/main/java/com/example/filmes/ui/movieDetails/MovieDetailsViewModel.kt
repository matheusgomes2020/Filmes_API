package com.example.filmes.ui.movieDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.CastX
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
    private val castEmitter = MutableLiveData<List<CastX>>()
    var carregando: Boolean = true
    val movieInfo: LiveData<Movie> = movieInfoEmitter
    val cast: LiveData<List<CastX>> = castEmitter

    fun getMovieInfo( movieId: String ) {

         try {
             viewModelScope.launch {
             when (val response = repository.getMovieInfo( movieId ) ){
                 is Resource.Success -> {
                     movieInfoEmitter.value = response.data!!
                     if (movieInfoEmitter.value!! != null) carregando = false
                     Log.e("Network", "upcomingMovies: Ok. Certo!!! Carregando?= $carregando")
                 }
                 is Resource.Error -> {
                     carregando = false
                     Log.e("Network", "upcomingMovies: Failed getting filmes Carregando?= $carregando")
                 }
                 else -> {
                     carregando = false
                 }
             }
         }
         }catch (exception: Exception) {
             carregando = false
             Log.d("Network", "upcomingMovies: ${exception.message.toString()} Carregando?= $carregando")
         }
     }

    fun getCast( movieId: String ) {

        try {
            viewModelScope.launch {
                when (val response = repository.getCast( movieId ) ){
                    is Resource.Success -> {
                        castEmitter.value = response.data!!
                        if (castEmitter.value!! != null) carregando = false
                        Log.e("Network", "cast: Ok. Certo!!! Carregando?= $carregando")
                    }
                    is Resource.Error -> {
                        carregando = false
                        Log.e("Network", "cast: Failed getting cast Carregando?= $carregando")
                    }
                    else -> {
                        carregando = false
                    }
                }
            }
        }catch (exception: Exception) {
            carregando = false
            Log.d("Network", "cast: ${exception.message.toString()} Carregando?= $carregando")
        }
    }

    }