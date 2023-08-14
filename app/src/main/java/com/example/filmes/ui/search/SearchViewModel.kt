package com.example.filmes.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.repository.MoviesRepository
import com.example.filmes.repository.SeriesRepository
import com.example.filmes.utils.MovieList2State
import com.example.filmes.utils.SeriesList2State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: MoviesRepository, private val seriesRepository: SeriesRepository)
    :ViewModel() {

    private val _searchMoviesData = MutableStateFlow(MovieList2State())
    private val _searchSeriesData = MutableStateFlow(SeriesList2State())
    val searchMoviesData: StateFlow<MovieList2State> = _searchMoviesData
    val searchSeriesData: StateFlow<SeriesList2State> = _searchSeriesData

    fun searchMovies(query: String) {
        repository.searchMovies( query ).onEach {
            when (it) {
                is Resource.Loading -> {
                    _searchMoviesData.value = MovieList2State(isLoading = true)
                }
                is Resource.Error -> {
                    _searchMoviesData.value = MovieList2State(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _searchMoviesData.value = MovieList2State(data = it.data!!)
                }

                else -> {}
            }
        }.launchIn( viewModelScope )

    }

    fun searchSeries(query: String) {
        seriesRepository.searchSeries( query ).onEach {
            when (it) {
                is Resource.Loading -> {
                    _searchSeriesData.value = SeriesList2State(isLoading = true)
                }
                is Resource.Error -> {
                    _searchSeriesData.value = SeriesList2State(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _searchSeriesData.value = SeriesList2State(data = it.data!!)
                }

                else -> {}
            }
        }.launchIn( viewModelScope )

    }
}