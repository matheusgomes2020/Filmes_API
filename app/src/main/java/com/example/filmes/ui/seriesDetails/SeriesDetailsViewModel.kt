package com.example.filmes.ui.seriesDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.Movie
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
    var carregando: Boolean = true
    val serieInfo: LiveData<Serie> = serieInfoEmitter

    fun getMovieInfo( serieId: String ) {

        try {
            viewModelScope.launch {
                when (val response = seriesRepository.getSerieInfo( serieId ) ){
                    is Resource.Success -> {
                        serieInfoEmitter.value = response.data!!
                        if (serieInfoEmitter.value!! != null) carregando = false
                        Log.e("Network", "upcomingMovies: Ok. Certo!!! Carregando?= $carregando")
                    }
                    is Resource.Error -> {
                        carregando = false
                        Log.e("Network", "upcomingMovies: Failed getting filmes Carregando?= $carregando")
                    }
                    else -> {
                        carregando = false
                    }
                }
            }
        }catch (exception: Exception) {
            carregando = false
            Log.d("Network", "upcomingMovies: ${exception.message.toString()} Carregando?= $carregando")
        }
    }

}