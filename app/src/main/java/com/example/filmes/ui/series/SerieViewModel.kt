package com.example.filmes.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.repository.SeriesRepository
import com.example.filmes.utils.SeriesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SerieViewModel @Inject constructor(private val seriesRepository: SeriesRepository)
    : ViewModel() {

    private val _popularSeries = MutableStateFlow(SeriesListState())
    private val _onTheAirSeries = MutableStateFlow(SeriesListState())
    private val _airingTodaySeries = MutableStateFlow(SeriesListState())
    private val _ratedSeries = MutableStateFlow(SeriesListState())
    val popularSeries: StateFlow<SeriesListState> = _popularSeries
    val airingTodaySeries: StateFlow<SeriesListState> = _airingTodaySeries
    val topRatedSeries: StateFlow<SeriesListState> = _ratedSeries
    val onTheAirSeries: StateFlow<SeriesListState> = _onTheAirSeries

     fun getPopularSeries() {
         seriesRepository.getPopularSeries().onEach {
             when (it) {
                 is Resource.Loading -> {
                     _popularSeries.value = SeriesListState(isLoading = true)
                 }
                 is Resource.Error -> {
                     _popularSeries.value = SeriesListState(error = it.message ?: "")
                 }
                 is Resource.Success -> {
                     _popularSeries.value = SeriesListState(data = it.data!!) }
                 else -> {}
             }
         }.launchIn(viewModelScope)
     }

    fun getAiringTodaySeries() {
        seriesRepository.getAiringTodaySeries().onEach {
            when (it) {
                is Resource.Loading -> {
                    _airingTodaySeries.value = SeriesListState(isLoading = true)
                }
                is Resource.Error -> {
                    _airingTodaySeries.value = SeriesListState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _airingTodaySeries.value = SeriesListState(data = it.data!!) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun getRatedSeries() {

        seriesRepository.getRatedSeries().onEach {
            when (it) {
                is Resource.Loading -> {
                    _ratedSeries.value = SeriesListState(isLoading = true)
                }
                is Resource.Error -> {
                    _ratedSeries.value = SeriesListState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _ratedSeries.value = SeriesListState(data = it.data!!) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun getOnTheAirSeries() {
        seriesRepository.getOnTheAirSeries().onEach {
            when (it) {
                is Resource.Loading -> {
                    _onTheAirSeries.value = SeriesListState(isLoading = true)
                }
                is Resource.Error -> {
                    _onTheAirSeries.value = SeriesListState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _onTheAirSeries.value = SeriesListState(data = it.data!!) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}