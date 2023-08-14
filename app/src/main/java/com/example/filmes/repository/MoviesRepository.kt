package com.example.filmes.repository

import com.example.filmes.data.Resource
import com.example.filmes.model.movie.Movie
import com.example.filmes.network.MoviesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviesRepository @Inject constructor( private val api: MoviesApi ) {

    fun searchMovies(searchQuery: String): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            val response =
                api.searchMovies(searchQuery).results
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "Check Your Internet Connection"
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }


     fun getUpcomingMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            val response =
                api.getUpcoming().results
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "Check Your Internet Connection"
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }

    fun getNowPlayingMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            val response =
                api.getNowPlaying().results
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "Check Your Internet Connection"
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }

    fun getRatedMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            val response =
                api.getRatedMovies().results
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "Check Your Internet Connection"
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }

    fun getPopularMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            val response =
                api.getPopularMovies().results
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "Check Your Internet Connection"
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }

    fun getMovieInfo(movieId: String): Flow<Resource<Movie>> = flow {
        emit(Resource.Loading())
        try {
            val response =
                api.getMovieInfo( movieId )
            emit(Resource.Success(data = response))
        }catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }
}