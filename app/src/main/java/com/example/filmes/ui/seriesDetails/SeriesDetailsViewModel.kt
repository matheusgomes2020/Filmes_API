package com.example.filmes.ui.seriesDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.serie.Season
import com.example.filmes.model.serie.Serie
import com.example.filmes.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SeriesDetailsViewModel @Inject constructor( private val seriesRepository: SeriesRepository )
    : ViewModel() {

    private var _seriesInfo = MutableLiveData<Serie>()
    private val _seasonEpisodes = MutableLiveData<Season>()
    var carregandoEmitter = MutableLiveData<Boolean>()
    val seriesInfo: LiveData<Serie> = _seriesInfo
    val seasonEpisodes: LiveData<Season> = _seasonEpisodes
    val carregando: LiveData<Boolean> = carregandoEmitter

    fun getSerieInfo(serieId: String ) {

        try {
            viewModelScope.launch {
                when (val response = seriesRepository.getSerieInfo( serieId ) ){
                    is Resource.Success -> {
                        _seriesInfo.value = response.data!!
                        if (_seriesInfo.value!! != null) carregandoEmitter.value = false
                        Log.e("Network", "Série: Ok. Certo!!! Carregando?= ${carregando.value}")
                        Log.e("Network", "getSerieInfo: " + seriesInfo.value)
                        Log.e("Network", "getSerieInfo: " + _seriesInfo.value)
                    }
                    is Resource.Error -> {
                        carregandoEmitter.value = false
                        Log.e("Network", "Série: Failed getting Séries Carregando?= ${carregando.value}")
                        Log.e("Network", "Failed getting Séries: " + seriesInfo.value)
                        Log.e("Network", "Failed getting Séries: " + _seriesInfo.value)
                    }
                    else -> {
                        carregandoEmitter.value = false
                    }
                }
            }
        }catch (exception: Exception) {
            carregandoEmitter.value = false
            Log.d("Network", "Série: ${exception.message.toString()} Carregando?= $carregando")
            Log.d("wwww", "getSerieInfo: " + _seriesInfo.value)
        }
    }

    fun getSeasonEpisodes( seriesId: String, seasonNumber: Int ) {

        try {
            viewModelScope.launch {
                when (val response = seriesRepository.getSeasonEpisodes( seriesId, seasonNumber ) ){
                    is Resource.Success -> {
                        _seasonEpisodes.value = response.data!!
                        if (_seasonEpisodes.value!! != null) carregandoEmitter.value = false
                        Log.e("Network", "seasonEpisodes: Ok. Certo!!! Carregando?= $carregando")
                    }
                    is Resource.Error -> {
                        carregandoEmitter.value = false
                        Log.e("Network", "seasonEpisodes: Failed getting filmes Carregando?= $carregando")
                    }
                    else -> {
                        carregandoEmitter.value = false
                    }
                }
            }
        }catch (exception: Exception) {
            carregandoEmitter.value = false
            Log.d("Network", "seasonEpisodes: ${exception.message.toString()} Carregando?= $carregando")
        }
    }


}