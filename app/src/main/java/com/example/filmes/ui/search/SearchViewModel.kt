package com.example.filmes.ui.search

import android.util.Log
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
class SearchViewModel @Inject constructor( private val repository: MoviesRepository)
    :ViewModel() {

    private var resultMoviesEmitter = MutableLiveData<List<Movie>>()

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

            resultMoviesEmitter.value = repository.searchMovies(query)

        }

    }


}