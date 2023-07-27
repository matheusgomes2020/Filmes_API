package com.example.filmes.ui.series

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
class SerieViewModel @Inject constructor(private val seriesRepository: SeriesRepository)
    : ViewModel() {

    private val popularSeriesEmitter = MutableLiveData<List<Serie>>()
    private val airingTodaySeriesEmitter = MutableLiveData<List<Serie>>()
    private val topRatedSeriesEmitter = MutableLiveData<List<Serie>>()
    private val onTheAirSeriesEmitter = MutableLiveData<List<Serie>>()
    var carregando: Boolean = true
    val popularSeries: LiveData<List<Serie>> = popularSeriesEmitter
    val airingTodaySeries: LiveData<List<Serie>> = airingTodaySeriesEmitter
    val topRatedSeries: LiveData<List<Serie>> = topRatedSeriesEmitter
    val onTheAirSeries: LiveData<List<Serie>> = onTheAirSeriesEmitter

    init {
        loadPopularSeries()
        loadAiringTodaySeries()
        loadOnTheAirSeries()
        loadTopRatedSeries()

        Log.e("Series", "Init!!! Carregando?= $carregando")
    }

    private fun loadPopularSeries() {

        viewModelScope.launch {
            try {
                when( val response = seriesRepository.getPopularSeries() ) {
                    is Resource.Success -> {
                        popularSeriesEmitter.value = response.data!!
                        if ( popularSeriesEmitter.value!!.isNotEmpty() ) carregando = false
                        Log.e("Series", "popularSeries: Ok. Certo!!! Carregando?= $carregando")
                    }
                    is Resource.Error -> {
                        carregando = false
                        Log.e("Series", "popularSeries: Failed getting séries Carregando?= $carregando")
                    }
                    else -> {
                        carregando = false
                    }
                }
            } catch ( exception: Exception ) {
                carregando = false
                Log.d("Series", "popularSeries: ${exception.message.toString()} Carregando?= $carregando")
            }
         }

    }

    private fun loadAiringTodaySeries() {

        viewModelScope.launch {
            try {
                when( val response = seriesRepository.getAiringTodaySeries() ) {
                    is Resource.Success -> {
                        airingTodaySeriesEmitter.value = response.data!!
                        if ( airingTodaySeriesEmitter.value!!.isNotEmpty() ) carregando = false
                        Log.e("Series", "popularSeries: Ok. Certo!!! Carregando?= $carregando")
                    }
                    is Resource.Error -> {
                        carregando = false
                        Log.e("Series", "popularSeries: Failed getting séries Carregando?= $carregando")
                    }
                    else -> {
                        carregando = false
                    }
                }
            } catch ( exception: Exception ) {
                carregando = false
                Log.d("Series", "popularSeries: ${exception.message.toString()} Carregando?= $carregando")
            }
        }

    }

    private fun loadTopRatedSeries() {

        viewModelScope.launch {
            try {
                when( val response = seriesRepository.getTopRatedSeries() ) {
                    is Resource.Success -> {
                        topRatedSeriesEmitter.value = response.data!!
                        if ( topRatedSeriesEmitter.value!!.isNotEmpty() ) carregando = false
                        Log.e("Series", "popularSeries: Ok. Certo!!! Carregando?= $carregando")
                    }
                    is Resource.Error -> {
                        carregando = false
                        Log.e("Series", "popularSeries: Failed getting séries Carregando?= $carregando")
                    }
                    else -> {
                        carregando = false
                    }
                }
            } catch ( exception: Exception ) {
                carregando = false
                Log.d("Series", "popularSeries: ${exception.message.toString()} Carregando?= $carregando")
            }
        }

    }

    private fun loadOnTheAirSeries() {

        viewModelScope.launch {
            try {
                when( val response = seriesRepository.getOnTheAirSeries() ) {
                    is Resource.Success -> {
                        onTheAirSeriesEmitter.value = response.data!!
                        if ( onTheAirSeriesEmitter.value!!.isNotEmpty() ) carregando = false
                        Log.e("Series", "popularSeries: Ok. Certo!!! Carregando?= $carregando")
                    }
                    is Resource.Error -> {
                        carregando = false
                        Log.e("Series", "popularSeries: Failed getting séries Carregando?= $carregando")
                    }
                    else -> {
                        carregando = false
                    }
                }
            } catch ( exception: Exception ) {
                carregando = false
                Log.d("Series", "popularSeries: ${exception.message.toString()} Carregando?= $carregando")
            }
        }

    }



}