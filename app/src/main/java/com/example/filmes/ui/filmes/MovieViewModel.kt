package com.example.filmes.ui.filmes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.Movie
import com.example.filmes.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val moviesRepository: MoviesRepository )
    : ViewModel() {

    private val _popularMovies = MutableLiveData<List<Movie>>()
    private val _ratedMovies = MutableLiveData<List<Movie>>()
    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    var carregando: Boolean = true
    val popularMovies: LiveData<List<Movie>> = _popularMovies
    val ratedMovies: LiveData<List<Movie>> = _ratedMovies
    val upcomingMovies: LiveData<List<Movie>> = _upcomingMovies
    val nowPlayingMovies: LiveData<List<Movie>> = _nowPlayingMovies

    init {
        loadPopularMovies()
        loadRatedMovies()
        loadUpcomingMovies()
        loadNowPlayingMovies()
        loadPopularMovies()

        Log.e("Network", "Init!!! Carregando?= $carregando")
    }

    private fun loadUpcomingMovies() {

        viewModelScope.launch {
            try {
                when (val response = moviesRepository.getUpcomingMovies()) {
                    is Resource.Success -> {
                        _upcomingMovies.value = response.data!!
                        if (_upcomingMovies.value!!.isNotEmpty()) carregando = false
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
            } catch (exception: Exception) {
                carregando = false
                Log.d("Network", "upcomingMovies: ${exception.message.toString()} Carregando?= $carregando")
            }
        }
    }

    private fun loadPopularMovies() {

        viewModelScope.launch {
            try {
                when (val response = moviesRepository.getPopularMovies()) {
                    is Resource.Success -> {
                        _popularMovies.value = response.data!!
                        if (_popularMovies.value!!.isNotEmpty()) carregando = false
                        Log.e("Network", "popularMovies: Ok. Certo!!! Carregando?= $carregando")
                    }
                    is Resource.Error -> {
                        carregando = false
                        Log.e("Network", "popularMovies: Failed getting filmes Carregando?= $carregando")
                    }
                    else -> {
                        carregando = false
                    }
                }
            } catch (exception: Exception) {
                carregando = false
                Log.d("Network", "popularMovies: ${exception.message.toString()} Carregando?= $carregando")
            }
        }
    }

    private fun loadRatedMovies() {

        viewModelScope.launch {
            try {
                when (val response = moviesRepository.getRatedMovies()) {
                    is Resource.Success -> {
                        _ratedMovies.value = response.data!!
                        if (_ratedMovies.value!!.isNotEmpty()) carregando = false
                        Log.e("Network", "ratedMovies: Ok. Certo!!! Carregando?= $carregando")
                    }
                    is Resource.Error -> {
                        carregando = false
                        Log.e("Network", "ratedMovies: Failed getting filmes Carregando?= $carregando")
                    }
                    else -> {
                        carregando = false
                    }
                }
            } catch (exception: Exception) {
                carregando = false
                Log.d("Network", "ratedMovies: ${exception.message.toString()} Carregando?= $carregando")
            }
        }
    }

    private fun loadNowPlayingMovies() {

        viewModelScope.launch {
            try {
                when (val response = moviesRepository.getNowPlayingMovies()) {
                    is Resource.Success -> {
                        _nowPlayingMovies.value = response.data!!
                        if (_nowPlayingMovies.value!!.isNotEmpty()) carregando = false
                        Log.e("Network", "nowPlayingMovies: Ok. Certo!!! Carregando?= $carregando")
                    }
                    is Resource.Error -> {
                        carregando = false
                        Log.e("Network", "nowPlayingMovies: Failed getting filmes Carregando?= $carregando")
                    }
                    else -> {
                        carregando = false
                    }
                }
            } catch (exception: Exception) {
                carregando = false
                Log.d("Network", "nowPlayingMovies: ${exception.message.toString()} Carregando?= $carregando")
            }
        }
    }
}