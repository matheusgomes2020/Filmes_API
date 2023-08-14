package com.example.filmes.ui.season

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.serie.Season
import com.example.filmes.repository.SeriesRepository
import com.example.filmes.utils.SeasonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SeasonViewModel @Inject constructor( private val seriesRepository: SeriesRepository)
    : ViewModel() {

    private val _seasonEpisodesData = MutableStateFlow(SeasonState())
    val seasonEpisodesData: StateFlow<SeasonState> = _seasonEpisodesData

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