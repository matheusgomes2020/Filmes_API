package com.example.filmes.ui.searchSeries

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.serie.Serie
import com.example.filmes.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SerieSearchViewModel @Inject constructor(private val repository: SeriesRepository)
    :ViewModel() {

    private var _series = MutableLiveData<List<Serie>>()
    var carregando: Boolean = true
    val series: LiveData<List<Serie>> = _series
    private val _state = MutableLiveData<String>()
    val state: LiveData<String> get() = _state

    init {

        _state.value = "Star wars"
    }


    fun searchSeries(query: String) {

        viewModelScope.launch {
            if (query.isEmpty()) {
                return@launch
            }
            try {
                when(val response = repository.searchSeries(query)) {
                    is Resource.Success -> {
                        _series.value = response.data!!
                        if (_series.value!!.isNotEmpty()) carregando = false
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
        }
    }
}