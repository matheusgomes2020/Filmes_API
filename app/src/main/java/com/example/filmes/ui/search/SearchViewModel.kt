package com.example.filmes.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.repository.MoviesRepository
import com.example.filmes.repository.SeriesRepository
import com.example.filmes.utils.MovieListState
import com.example.filmes.utils.SeriesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: MoviesRepository, private val seriesRepository: SeriesRepository)
    :ViewModel() {

    private val _searchMoviesData = MutableStateFlow(MovieListState())
    private val _searchSeriesData = MutableStateFlow(SeriesListState())
    val searchMoviesData: StateFlow<MovieListState> = _searchMoviesData
    val searchSeriesData: StateFlow<SeriesListState> = _searchSeriesData

    fun searchMovies(query: String) {
        repository.searchMovies( query ).onEach {
            when (it) {
                is Resource.Loading -> {
                    _searchMoviesData.value = MovieListState(isLoading = true)
                }
                is Resource.Error -> {
                    _searchMoviesData.value = MovieListState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _searchMoviesData.value = MovieListState(data = it.data!!)
                }

                else -> {}
            }
        }.launchIn( viewModelScope )

    }

    fun searchSeries(query: String) {
        seriesRepository.searchSeries( query ).onEach {
            when (it) {
                is Resource.Loading -> {
                    _searchSeriesData.value = SeriesListState(isLoading = true)
                }
                is Resource.Error -> {
                    _searchSeriesData.value = SeriesListState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _searchSeriesData.value = SeriesListState(data = it.data!!)
                }

                else -> {}
            }
        }.launchIn( viewModelScope )

    }
}