package com.example.filmes.repository

import android.util.Log
import com.example.filmes.data.Resource
import com.example.filmes.model.movie.Movie
import com.example.filmes.model.serie.Serie
import com.example.filmes.model.serie.Season
import com.example.filmes.model.serie.Episode
import com.example.filmes.network.SeriesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SeriesRepository @Inject constructor( private val api: SeriesApi) {

    fun searchSeries(searchQuery: String): Flow<Resource<List<Serie>>> = flow {
        emit(Resource.Loading())
        try {
            val response =
                api.searchSeries(searchQuery).results
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

    fun getPopularSeries(): Flow<Resource<List<Serie>>> = flow {
        emit(Resource.Loading())
        try {
            val response =
                api.getPopularSeries().results
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

    fun getOnTheAirSeries(): Flow<Resource<List<Serie>>> = flow {
        emit(Resource.Loading())
        try {
            val response =
                api.getOnTheAirSeries().results
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

    fun getRatedSeries(): Flow<Resource<List<Serie>>> = flow {
        emit(Resource.Loading())
        try {
            val response =
                api.getPopularTopRated().results
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

    fun getAiringTodaySeries(): Flow<Resource<List<Serie>>> = flow {
        emit(Resource.Loading())
        try {
            val response =
                api.getAiringTodaySeries().results
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

    fun getSeriesInfo(seriesId: String): Flow<Resource<Serie>> = flow {
        emit(Resource.Loading())
        try {
            val response =
                api.getSeriesInfo( seriesId )
            emit(Resource.Success(data = response))
        }catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }

    fun getSeasonEpisodes(seriesId: String, seasonNumber: Int): Flow<Resource<Season>> = flow {
        emit(Resource.Loading())
        try {
            val response =
                api.getSeasonEpisodes( seriesId, seasonNumber )
            emit(Resource.Success(data = response))
        }catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }

    fun getEpisodeInfo(seriesId: String, seasonNumber: Int, episodeNumber: Int ): Flow<Resource<Episode>> = flow {
        emit(Resource.Loading())
        try {
            val response =
                api.getEpisodeInfo( seriesId, seasonNumber, episodeNumber )
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