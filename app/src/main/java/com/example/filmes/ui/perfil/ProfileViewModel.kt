package com.example.filmes.ui.perfil

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.MovieD
import com.example.filmes.model.movie.Movie
import com.example.filmes.repository.MRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor( private val repository: MRepository)
    : ViewModel() {

    private val _movieList = MutableLiveData<List<MovieD>>()
    var carregando: Boolean = true
    val movieList:  LiveData<List<MovieD>> = _movieList

    init {
        loadMovies()
    }


    fun addMovie( movie: MovieD ) = viewModelScope.launch {

        try {

            repository.addMovie( movie )

        }catch (exception: Exception) {
            carregando = false
            Log.d("Network", "ADD MOVIE: ${exception.message.toString()} Carregando?= $carregando")
            Log.d("ROOMST", "ProfileViewModel " + movie.toString())
        }

         }

    private fun loadMovies() {

        viewModelScope.launch {

            try {
                val response = repository.getAllMovies()
                _movieList.value = response

            } catch (exception: Exception) {
                carregando = false
                Log.d(
                    "Network",
                    "Profile: ${exception.message.toString()} Carregando?= $carregando"
                )
            }


        }


    }

}



