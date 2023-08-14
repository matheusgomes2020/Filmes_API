package com.example.filmes.ui.seriesDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.repository.SeriesRepository
import com.example.filmes.utils.SeasonState
import com.example.filmes.utils.Serie2State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SeriesDetailsViewModel @Inject constructor( private val seriesRepository: SeriesRepository)
    : ViewModel() {

    private val _seriesData = MutableStateFlow(Serie2State())
    val seriesData: StateFlow<Serie2State> = _seriesData
    private val _seasonEpisodesData = MutableStateFlow(SeasonState())
    val seasonEpisodesData: StateFlow<SeasonState> = _seasonEpisodesData

    fun getSerieInfo(serieId: String ) {
        seriesRepository.getSeriesInfo( serieId ).onEach {
            when (it) {
                is Resource.Loading -> {
                    _seriesData.value = Serie2State(isLoading = true)
                }
                is Resource.Error -> {
                    _seriesData.value = Serie2State(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _seriesData.value = Serie2State(data = it.data)
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }


    fun getSeasonEpisodes( seriesId: String, seasonNumber: Int ) {
        seriesRepository.getSeasonEpisodes( seriesId, seasonNumber ).onEach {
            when (it) {
                is Resource.Loading -> {
                    _seasonEpisodesData.value = SeasonState(isLoading = true)
                }
                is Resource.Error -> {
                    _seasonEpisodesData.value = SeasonState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _seasonEpisodesData.value = SeasonState(data = it.data)
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
        }


}