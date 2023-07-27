package com.example.filmes.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.movie.Movie
import com.example.filmes.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor( private val repository: MoviesRepository)
    :ViewModel() {

    private var resultMoviesEmitter = MutableLiveData<List<Movie>>()
    var carregando: Boolean = true
    val popularMovies: LiveData<List<Movie>> = resultMoviesEmitter
    private val _state = MutableLiveData<String>()
    val state: LiveData<String> get() = _state

    init {

        _state.value = "Star wars"
        loadMovies()
    }

    private fun  loadMovies() {
        //state.value?.let { searchMovies(it) }
        //Log.d("SSTATE", "FILLLLME: " + state.value)
    }

    fun searchMovies(query: String) {

        viewModelScope.launch {
            if (query.isEmpty()) {
                return@launch
            }
            try {
                when(val response = repository.searchMovies(query)) {
                    is Resource.Success -> {
                        resultMoviesEmitter.value = response.data!!
                        if (resultMoviesEmitter.value!!.isNotEmpty()) carregando = false
                        Log.e("Network", "searchMovies: Ok. Certo!!! Carregando?= $carregando")
                    }
                    is Resource.Error -> {
                        carregando = false
                        Log.e("Network", "searchMovies: Failed getting filmes Carregando?= $carregando")
                    }
                    else -> { carregando = false }
                }
            }catch (exception: Exception){
                carregando = false
                Log.d("Network", "searchMovies: ${exception.message.toString()} Carregando?= $carregando")
            }
        //resultMoviesEmitter.value = repository.searchMovies(query)
        }
    }
}