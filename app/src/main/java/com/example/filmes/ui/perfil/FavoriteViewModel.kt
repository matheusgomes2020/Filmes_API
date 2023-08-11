package com.example.filmes.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.MovieFirebase
import com.example.filmes.model.SeriesFirebase
import com.example.filmes.repository.FireRepository
import com.example.filmes.repository.RoomRepository
import com.example.filmes.utils.MovieListState
import com.example.filmes.utils.SeriesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: RoomRepository, private val fireRepository: FireRepository)
    : ViewModel() {

    private val _movieList = MutableStateFlow(MovieListState())
    val movieList: StateFlow<MovieListState> = _movieList

    private val _seriesList = MutableStateFlow(SeriesListState())
    val seriesList: StateFlow<SeriesListState> = _seriesList

    fun saveMovie(movie: MovieFirebase) {
        fireRepository.saveMovie(movie).onEach {
        }.launchIn(viewModelScope)
    }

    fun deleteMovie(movie: MovieFirebase) {
        fireRepository.deleteMovie(movie).onEach {
        }.launchIn(viewModelScope)
    }
    fun getMovies() {
        fireRepository.getMovies().onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieList.value = MovieListState(isLoading = true)}
                is Resource.Error -> {
                    _movieList.value = MovieListState(error = it.message ?: "")}
                is Resource.Success -> {
                    _movieList.value = MovieListState(data = it.data!!) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun saveSeries( seriesFirebase: SeriesFirebase ) {
        fireRepository.saveSeries(seriesFirebase).onEach {
        }.launchIn(viewModelScope)
    }

    fun deleteSeries( series: SeriesFirebase ) {
        fireRepository.deleteSeries( series ).onEach {
        }.launchIn(viewModelScope)
    }
    fun getSeries() {
        fireRepository.getSeries().onEach {
            when (it) {
                is Resource.Loading -> {
                    _seriesList.value = SeriesListState(isLoading = true)}
                is Resource.Error -> {
                    _seriesList.value = SeriesListState(error = it.message ?: "")}
                is Resource.Success -> {
                    _seriesList.value = SeriesListState(data = it.data!!) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}



