package com.example.filmes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.Movie
import com.example.filmes.model.Movies
import com.example.filmes.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor( private val moviesRepository: MoviesRepository )
    : ViewModel() {


    private val cc = MutableLiveData<List<Movie>>()

    val cc1: LiveData<List<Movie>> = cc


    init {

        loadPopularMovies()

    }


    private fun loadPopularMovies() {

        viewModelScope.launch {

            cc.value = moviesRepository.getPopularMovies()

        }

    }

}