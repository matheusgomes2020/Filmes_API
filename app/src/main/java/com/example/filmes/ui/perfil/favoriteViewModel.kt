package com.example.filmes.ui.perfil

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.model.MovieRoom
import com.example.filmes.model.SerieRoom
import com.example.filmes.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class favoriteViewModel @Inject constructor(private val repository: RoomRepository)
    : ViewModel() {

    private val _movieList = MutableLiveData<List<MovieRoom>>()
    private val _seriesList = MutableLiveData<List<SerieRoom>>()
    var carregando: Boolean = true
    val movieList:  LiveData<List<MovieRoom>> = _movieList
    val seriesList:  LiveData<List<SerieRoom>> = _seriesList

    init {
        loadMovies()
        loadSeries()
    }

     fun deleteMovie( movie: MovieRoom ) = viewModelScope.launch { repository.deleteMovie( movie )
    loadMovies()}

    fun deleteSeries( series: SerieRoom ) = viewModelScope.launch { repository.deleteSeries( series )
        loadSeries()}

     fun deleteAllMovies() = viewModelScope.launch { repository.deleteAllMovies()
    loadMovies()}

    private fun loadMovies() {

        viewModelScope.launch {
            try {
                val response = repository.getAllMovies()
                _movieList.value = response
            } catch (exception: Exception) {
                carregando = false
                Log.d("Network", "Profile: ${exception.message.toString()} Carregando?= $carregando")
            }
        }
    }

    private fun loadSeries() {

        viewModelScope.launch {
            try {
                val response = repository.getAllSeries()
                _seriesList.value = response
            } catch (exception: Exception) {
                carregando = false
                Log.d("Network", "Profile: ${exception.message.toString()} Carregando?= $carregando")
            }
        }
    }

}



