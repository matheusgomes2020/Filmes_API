package com.example.filmes.ui.episodes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.serie.Episode
import com.example.filmes.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(private val seriesRepository: SeriesRepository)
    : ViewModel() {

    private var _episodeInfo = MutableLiveData<Episode>()
    var carregando: Boolean = true
    val episodeInfo: LiveData<Episode> = _episodeInfo


    fun getEpisodeInfo( seriesId: String, seasonNumber: Int, episodeNumber: Int ) {

        Log.d( "UTG", "ViewModel: " + "Id: " + seriesId + "\nSeason: " + seasonNumber + "\nEpisode: " + episodeNumber)


        try {
            viewModelScope.launch {
                when (val response = seriesRepository.getEpisodeInfo( seriesId, seasonNumber, episodeNumber ) ){
                    is Resource.Success -> {
                        _episodeInfo.value = response.data!!
                        if (_episodeInfo.value!! != null) carregando = false
                        Log.e("Network", "Episódio: Ok. Certo!!! Carregando?= ${carregando}")
                        Log.e("Network", "getEpisódioInfo: " + episodeInfo.value)
                        Log.e("Network", "getEpisódioInfo: " + _episodeInfo.value)
                    }
                    is Resource.Error -> {
                        carregando = false
                        Log.e("Network", "Episódio: Failed getting Episódio Carregando?= ${carregando}")
                        Log.e("Network", "Failed getting Episódio: " + episodeInfo.value)
                        Log.e("Network", "Failed getting Episódio: " + _episodeInfo.value)
                    }
                    else -> {
                        carregando = false
                    }
                }
            }
        }catch (exception: Exception) {
            carregando = false
            Log.d("Network", "Episódio: ${exception.message.toString()} Carregando?= $carregando")
            Log.d("wwww", "getEpisódioInfo: " + _episodeInfo.value)
        }

    }

}