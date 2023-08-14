package com.example.filmes.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.repository.SeriesRepository
import com.example.filmes.utils.SeriesList2State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SerieViewModel @Inject constructor(private val seriesRepository: SeriesRepository)
    : ViewModel() {

    private val _popularSeries = MutableStateFlow(SeriesList2State())
    private val _onTheAirSeries = MutableStateFlow(SeriesList2State())
    private val _airingTodaySeries = MutableStateFlow(SeriesList2State())
    private val _ratedSeries = MutableStateFlow(SeriesList2State())
    val popularSeries: StateFlow<SeriesList2State> = _popularSeries
    val airingTodaySeries: StateFlow<SeriesList2State> = _airingTodaySeries
    val topRatedSeries: StateFlow<SeriesList2State> = _ratedSeries
    val onTheAirSeries: StateFlow<SeriesList2State> = _onTheAirSeries

     fun getPopularSeries() {
         seriesRepository.getPopularSeries().onEach {
             when (it) {
                 is Resource.Loading -> {
                     _popularSeries.value = SeriesList2State(isLoading = true)
                 }
                 is Resource.Error -> {
                     _popularSeries.value = SeriesList2State(error = it.message ?: "")
                 }
                 is Resource.Success -> {
                     _popularSeries.value = SeriesList2State(data = it.data!!) }
                 else -> {}
             }
         }.launchIn(viewModelScope)
     }

    fun getAiringTodaySeries() {
        seriesRepository.getAiringTodaySeries().onEach {
            when (it) {
                is Resource.Loading -> {
                    _airingTodaySeries.value = SeriesList2State(isLoading = true)
                }
                is Resource.Error -> {
                    _airingTodaySeries.value = SeriesList2State(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _airingTodaySeries.value = SeriesList2State(data = it.data!!) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun getRatedSeries() {

        seriesRepository.getRatedSeries().onEach {
            when (it) {
                is Resource.Loading -> {
                    _ratedSeries.value = SeriesList2State(isLoading = true)
                }
                is Resource.Error -> {
                    _ratedSeries.value = SeriesList2State(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _ratedSeries.value = SeriesList2State(data = it.data!!) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun getOnTheAirSeries() {
        seriesRepository.getOnTheAirSeries().onEach {
            when (it) {
                is Resource.Loading -> {
                    _onTheAirSeries.value = SeriesList2State(isLoading = true)
                }
                is Resource.Error -> {
                    _onTheAirSeries.value = SeriesList2State(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _onTheAirSeries.value = SeriesList2State(data = it.data!!) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}