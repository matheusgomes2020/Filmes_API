package com.example.filmes.ui.movieDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.MovieRoom
import com.example.filmes.model.movie.Movie
import com.example.filmes.repository.RoomRepository
import com.example.filmes.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor( private val repository: MoviesRepository, private val roomRepository: RoomRepository )
    : ViewModel() {

    private var _movieInfo = MutableLiveData<Movie>()
    var carregando: Boolean = true
    val movieInfo: LiveData<Movie> = _movieInfo

    fun addMovie(movie: MovieRoom) = viewModelScope.launch { roomRepository.addMovie(movie) }
    fun getMovieInfo( movieId: String ) {

         try {
             viewModelScope.launch {
             when (val response = repository.getMovieInfo( movieId ) ){
                 is Resource.Success -> {
                     _movieInfo.value = response.data!!
                     if (_movieInfo.value!! != null) carregando = false
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

    }