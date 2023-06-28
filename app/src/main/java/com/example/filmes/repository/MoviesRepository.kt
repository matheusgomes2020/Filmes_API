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

    suspend fun getUpcoming(): List<Movie> {

        return api.getUpcoming().results

    }
    suspend fun getPopularMovies(): List<Movie> {

        return api.getPopularMovies().results

    }

    suspend fun getRatedMovies(): List<Movie> {

        return api.getRatedMovies().results
    }

    suspend fun getNowPlayingMovies(): List<Movie> {

        return api.getNowPlaying().results
    }

    suspend fun getNowPlayingMovies2(): Resource<List<Movie>> {

        return try {
            Resource.Loading( data = true )
            val itemList = api.getNowPlaying().results
            if ( itemList.isNotEmpty() ) Resource.Loading(data = false)
            Resource.Success( data = itemList )
        } catch ( exception: Exception ) {
            Resource.Error( message = exception.message.toString() )
        }
    }

    suspend fun getMovieInfo( movieId: String ): Movie {

        return api.getMovieInfo( movieId )

    }

    suspend fun getMovieInfo2(  ): Movie {

        return api.getMovieInfo2( )

    }

}