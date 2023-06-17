package com.example.filmes.repository

import com.example.filmes.data.Resource
import com.example.filmes.model.Movie
import com.example.filmes.network.MoviesApi
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor( private val api: MoviesApi ) {

    suspend fun searchMovies(searchQuery: String): List<Movie> {

        return api.searchMovies( searchQuery ).results

    }
    suspend fun getPopularMovies(): List<Movie> {

        return api.getPopularMovies().results

    }

    suspend fun getRatedMovies(): List<Movie> {

        return api.getRatedMovies().results

    }

    suspend fun getMovieInfo( movieId: String ): Movie {

        return api.getMovieInfo( movieId )

    }

    suspend fun getMovieInfo2(  ): Movie {

        return api.getMovieInfo2( )

    }

}