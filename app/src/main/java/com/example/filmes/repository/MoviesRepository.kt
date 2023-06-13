package com.example.filmes.repository

import com.example.filmes.data.Resource
import com.example.filmes.model.Movie
import com.example.filmes.network.MoviesApi
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor( private val api: MoviesApi ) {

    suspend fun getPopularMovies(): List<Movie> {


        return api.getPopularMovies().results
    }

}