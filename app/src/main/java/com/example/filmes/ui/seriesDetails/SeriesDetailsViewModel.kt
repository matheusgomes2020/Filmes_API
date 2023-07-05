package com.example.filmes.ui.seriesDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.CastX
import com.example.filmes.model.Episode
import com.example.filmes.model.Serie
import com.example.filmes.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SeriesDetailsViewModel @Inject constructor( private val seriesRepository: SeriesRepository )
    : ViewModel() {

    private var serieInfoEmitter = MutableLiveData<Serie>()
    private val seasonEpisodesEmitter = MutableLiveData<List<Episode>>()
    private val castEmitter = MutableLiveData<List<CastX>>()
    var carregandoEmitter = MutableLiveData<Boolean>()
    val serieInfo: LiveData<Serie> = serieInfoEmitter
    val seasonEpisodes: LiveData<List<Episode>> = seasonEpisodesEmitter
    val cast: LiveData<List<CastX>> = castEmitter
    val carregando: LiveData<Boolean> = carregandoEmitter

    fun getSerieInfo(serieId: String ) {

        try {
            viewModelScope.launch {
                when (val response = seriesRepository.getSerieInfo( serieId ) ){
                    is Resource.Success -> {
                        serieInfoEmitter.value = response.data!!
                        if (serieInfoEmitter.value!! != null) carregandoEmitter.value = false
                        Log.e("Network", "Série: Ok. Certo!!! Carregando?= ${carregando.value}")
                        Log.e("Network", "getSerieInfo: " + serieInfo.value)
                        Log.e("Network", "getSerieInfo: " + serieInfoEmitter.value)
                    }
                    is Resource.Error -> {
                        carregandoEmitter.value = false
                        Log.e("Network", "Série: Failed getting Séries Carregando?= ${carregando.value}")
                        Log.e("Network", "Failed getting Séries: " + serieInfo.value)
                        Log.e("Network", "Failed getting Séries: " + serieInfoEmitter.value)
                    }
                    else -> {
                        carregandoEmitter.value = false
                    }
                }
            }
        }catch (exception: Exception) {
            carregandoEmitter.value = false
            Log.d("Network", "Série: ${exception.message.toString()} Carregando?= $carregando")
            Log.d("wwww", "getSerieInfo: " + serieInfoEmitter.value)
        }
    }

    fun getSeasonEpisodes( seriesId: String, seasonNumber: Int ) {

        try {
            viewModelScope.launch {
                when (val response = seriesRepository.getSeasonEpisodes( seriesId, seasonNumber ) ){
                    is Resource.Success -> {
                        seasonEpisodesEmitter.value = response.data!!
                        if (seasonEpisodesEmitter.value!! != null) carregandoEmitter.value = false
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

    fun getCast( seriesId: String ) {

        try {
            viewModelScope.launch {
                when (val response = seriesRepository.getCast( seriesId ) ){
                    is Resource.Success -> {
                        castEmitter.value = response.data!!
                        if (castEmitter.value!! != null) carregandoEmitter.value = false
                        Log.e("Network", "cast: Ok. Certo!!! Carregando?= $carregando")
                    }
                    is Resource.Error -> {
                        carregandoEmitter.value = false
                        Log.e("Network", "cast: Failed getting cast Carregando?= $carregando")
                    }
                    else -> {
                        carregandoEmitter.value = false
                    }
                }
            }
        }catch (exception: Exception) {
            carregandoEmitter.value = false
            Log.d("Network", "cast: ${exception.message.toString()} Carregando?= $carregando")
        }
    }

}