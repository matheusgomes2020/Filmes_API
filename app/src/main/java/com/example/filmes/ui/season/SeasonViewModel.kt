package com.example.filmes.ui.season

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.Episode
import com.example.filmes.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SeasonViewModel @Inject constructor( private val seriesRepository: SeriesRepository)
    : ViewModel() {

    private val _season = MutableLiveData<List<Episode>>()
    var carregando: Boolean = true
    val season: LiveData<List<Episode>> = _season

    fun loadSeason( seriesId: String, seasonNumber: Int ) {

        try {
            viewModelScope.launch {
                when (val response = seriesRepository.getSeasonEpisodes( seriesId, seasonNumber ) ){
                    is Resource.Success -> {
                        _season.value = response.data!!
                        if (_season.value!! != null) carregando = false
                        Log.e("Network", "season: Ok. Certo!!! Carregando?= $carregando")
                    }
                    is Resource.Error -> {
                        carregando = false
                        Log.e("Network", "season: Failed getting season Carregando?= $carregando")
                    }
                    else -> {
                        carregando = false
                    }
                }
            }
        }catch (exception: Exception) {
            carregando = false
            Log.d("Network", "season: ${exception.message.toString()} Carregando?= $carregando")
        }
    }

}