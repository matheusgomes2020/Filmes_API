package com.example.filmes.ui.episodes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.serie.Episode
import com.example.filmes.repository.SeriesRepository
import com.example.filmes.utils.EpisodeState
import com.example.filmes.utils.Serie2State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(private val seriesRepository: SeriesRepository)
    : ViewModel() {

    private var _episodeData = MutableStateFlow(EpisodeState())
    val episodeData: StateFlow<EpisodeState> = _episodeData

    fun getEpisodeInfo( seriesId: String, seasonNumber: Int, episodeNumber: Int ) {
        seriesRepository.getEpisodeInfo( seriesId, seasonNumber, episodeNumber ).onEach {
            when (it) {
                is Resource.Loading -> {
                    _episodeData.value = EpisodeState(isLoading = true)
                }
                is Resource.Error -> {
                    _episodeData.value = EpisodeState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _episodeData.value = EpisodeState(data = it.data)
                }

                else -> {}
            }
        }.launchIn( viewModelScope )
    }

}