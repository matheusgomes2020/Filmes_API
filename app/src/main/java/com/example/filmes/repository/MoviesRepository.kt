package com.example.filmes.repository

import com.example.filmes.data.Resource
import com.example.filmes.model.CastX
import com.example.filmes.model.Movie
import com.example.filmes.network.MoviesApi
import javax.inject.Inject

class MoviesRepository @Inject constructor( private val api: MoviesApi ) {

    suspend fun searchMovies(searchQuery: String): Resource<List<Movie>> {

        return try {
            Resource.Loading(data = true)
            val itemList = api.searchMovies(searchQuery).results
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }
    }

    suspend fun getUpcomingMovies(): Resource<List<Movie>> {

        return try {
            Resource.Loading(data = true)
            val itemList = api.getUpcoming().results
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }
    }
    suspend fun getPopularMovies(): Resource<List<Movie>> {

        return try {
            Resource.Loading(data = true)
            val itemList = api.getPopularMovies().results
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }
    }

    suspend fun getRatedMovies(): Resource<List<Movie>> {

        return try {
            Resource.Loading(data = true)
            val itemList = api.getRatedMovies().results
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }
    }

    suspend fun getNowPlayingMovies(): Resource<List<Movie>> {

        return try {
            Resource.Loading( data = true )
            val itemList = api.getNowPlaying().results
            if ( itemList.isNotEmpty() ) Resource.Loading(data = false)
            Resource.Success( data = itemList )
        } catch ( exception: Exception ) {
            Resource.Error( message = exception.message.toString() )
        }
    }

    suspend fun getMovieInfo(movieId: String): Resource<Movie> {

        val response = try {
            Resource.Loading( data = true )
            api.getMovieInfo( movieId )
        }catch ( exception: Exception ) {
            return Resource.Error( message = "An error occurred ${exception.message.toString()}" )
        }
        Resource.Loading( data = false )
        return Resource.Success( data = response )
    }

    suspend fun getCast( movieId: String ): Resource<List<CastX>> {

        return try {
            Resource.Loading( data = true )
            val itemList = api.getCast( movieId ).cast
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }
    }



}