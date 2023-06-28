package com.example.filmes.ui.main

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
class MainViewModel @Inject constructor( private val moviesRepository: MoviesRepository )
    : ViewModel() {


    private val popularMoviesEmitter = MutableLiveData<List<Movie>>()
    private val ratedMoviesEmitter = MutableLiveData<List<Movie>>()
    private val upcomingMoviesEmitter = MutableLiveData<List<Movie>>()
    private val nowPlayingMoviesEmitter = MutableLiveData<List<Movie>>()
    private val nowPlayingMoviesEmitter2 = MutableLiveData<List<Movie>>()

    var carr: Boolean = true



    val popularMovies: LiveData<List<Movie>> = popularMoviesEmitter
    val ratedMovies: LiveData<List<Movie>> = ratedMoviesEmitter
    val upcomingMovies: LiveData<List<Movie>> = upcomingMoviesEmitter
    val nowPlayingovies: LiveData<List<Movie>> = nowPlayingMoviesEmitter
    val nowPlayingovies2: LiveData<List<Movie>> = nowPlayingMoviesEmitter2


    init {

        loadPopularMovies()
        loadRatedMovies()
        loadUpcoming()
        loadNowPlayingMovies()
        loadPopularMovies2()

        Log.e("Network", "Init!!! Carregando?= $carr")


    }

    private fun loadUpcoming() {

        viewModelScope.launch {

            upcomingMoviesEmitter.value = moviesRepository.getUpcoming()

        }

    }

    private fun loadPopularMovies() {

        viewModelScope.launch {

            popularMoviesEmitter.value = moviesRepository.getPopularMovies()

        }

    }

    private fun loadPopularMovies2() {

        viewModelScope.launch {

            try {
                when (val response = moviesRepository.getNowPlayingMovies2()) {
                    is Resource.Success -> {

                        nowPlayingMoviesEmitter2.value = response.data!!
                        if (nowPlayingMoviesEmitter2.value!!.isNotEmpty()) carr = false
                        Log.e("Network", "popularMovies: Ok. Certo!!! Carregando?= $carr")

                    }

                    is Resource.Error -> {
                        carr = false
                        Log.e("Network", "popularMovies: Failed getting filmes Carregando?= $carr")
                    }

                    else -> {
                        carr = false
                    }
                }
            } catch (exception: Exception) {
                carr = false
                Log.d("Network", "popularMovies: ${exception.message.toString()} Carregando?= $carr")


            }

        }
    }

    private fun loadRatedMovies() {

        viewModelScope.launch {

            ratedMoviesEmitter.value = moviesRepository.getRatedMovies()

        }

    }

    private fun loadNowPlayingMovies() {

        viewModelScope.launch {

            nowPlayingMoviesEmitter.value = moviesRepository.getNowPlayingMovies()

        }

    }

}