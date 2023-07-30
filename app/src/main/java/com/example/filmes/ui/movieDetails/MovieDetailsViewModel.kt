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
    private val _movieList = MutableLiveData<List<MovieRoom>>()
    var carregando: Boolean = true
    val movieInfo: LiveData<Movie> = _movieInfo
    val movieList:  LiveData<List<MovieRoom>> = _movieList

    init {
        loadMovies()
    }

    fun addMovie(movie: MovieRoom) = viewModelScope.launch { roomRepository.addMovie(movie) }

    fun deleteMovie(movie: MovieRoom) = viewModelScope.launch { roomRepository.deleteMovie(movie) }
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

    private fun loadMovies() {

        viewModelScope.launch {
            try {
                val response = roomRepository.getAllMovies()
                _movieList.value = response
            } catch (exception: Exception) {
                carregando = false
                Log.d("Network", "Profile: ${exception.message.toString()} Carregando?= $carregando")
            }
        }
    }

    }