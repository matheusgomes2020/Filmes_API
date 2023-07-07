package com.example.filmes.ui.searchSeries

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.Serie
import com.example.filmes.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SerieSearchViewModel @Inject constructor(private val repository: SeriesRepository)
    :ViewModel() {

    private var resultSeriesEmitter = MutableLiveData<List<Serie>>()
    var carregando: Boolean = true
    val series: LiveData<List<Serie>> = resultSeriesEmitter
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
                when(val response = repository.searchSeries(query)) {
                    is Resource.Success -> {
                        resultSeriesEmitter.value = response.data!!
                        if (resultSeriesEmitter.value!!.isNotEmpty()) carregando = false
                        Log.e("Network", "searchSeries: Ok. Certo!!! Carregando?= $carregando")
                    }
                    is Resource.Error -> {
                        carregando = false
                        Log.e("Network", "searchSeries: Failed getting séries Carregando?= $carregando")
                    }
                    else -> { carregando = false }
                }
            }catch (exception: Exception){
                carregando = false
                Log.d("Network", "searchSeries: ${exception.message.toString()} Carregando?= $carregando")
            }
        //resultMoviesEmitter.value = repository.searchMovies(query)
        }
    }
}