package com.example.filmes.ui.perfil

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.model.MovieD
import com.example.filmes.repository.MovieRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor( private val repository: MovieRoomRepository)
    : ViewModel() {

    private val _movieList = MutableLiveData<List<MovieD>>()
    var carregando: Boolean = true
    val movieList:  LiveData<List<MovieD>> = _movieList

    init {
        loadMovies()
    }

     fun deleteMovie( movie: MovieD ) = viewModelScope.launch { repository.deleteMovie( movie )
    loadMovies()}

     fun deleteAllMovies() = viewModelScope.launch { repository.deleteAllMovies()
    loadMovies()}

    private fun loadMovies() {

        viewModelScope.launch {
            try {
                val response = repository.getAllMovies()
                _movieList.value = response
            } catch (exception: Exception) {
                carregando = false
                Log.d("Network", "Profile: ${exception.message.toString()} Carregando?= $carregando")
            }
        }
    }
}



