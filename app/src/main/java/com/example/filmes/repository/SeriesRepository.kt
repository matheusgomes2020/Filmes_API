package com.example.filmes.repository

import com.example.filmes.data.Resource
import com.example.filmes.model.Season
import com.example.filmes.model.CastX
import com.example.filmes.model.Serie
import com.example.filmes.network.SeriesApi
import javax.inject.Inject

class SeriesRepository @Inject constructor( private val api: SeriesApi) {

    suspend fun searchSeries(searchQuery: String): Resource<List<Serie>> {

        return try {
            Resource.Loading(data = true)
            val itemList = api.searchSeries(searchQuery).results
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }
    }

    suspend fun getPopularSeries(): Resource<List<Serie>> {

        return try {
            Resource.Loading( data = true )
            val itemList = api.getPopularSeries().results
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }
    }

    suspend fun getAiringTodaySeries(): Resource<List<Serie>> {

        return try {
            Resource.Loading( data = true )
            val itemList = api.getAiringTodaySeries().results
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }
    }

    suspend fun getTopRatedSeries(): Resource<List<Serie>> {

        return try {
            Resource.Loading( data = true )
            val itemList = api.getPopularTopRated().results
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }
    }

    suspend fun getOnTheAirSeries(): Resource<List<Serie>> {

        return try {
            Resource.Loading( data = true )
            val itemList = api.getOnTheAirSeries().results
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }
    }



    suspend fun getSerieInfo(serieId: String): Resource<Serie> {

        val response = try {
            Resource.Loading( data = true )
            api.getSerieInfo( serieId )
        }catch ( exception: Exception ) {
            return Resource.Error( message = "An error occurred ${exception.message.toString()}" )
        }
        Resource.Loading( data = false )
        return Resource.Success( data = response )
    }

    suspend fun getSeasonEpisodes( seriesId: String, seasonNumber: Int ): Resource<Season> {

        return try {
            Resource.Loading( data = true )
            val itemList = api.getSeasonEpisodes( seriesId, seasonNumber )
            if (itemList.episodes.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }
    }

    suspend fun getCast( seriesId: String ): Resource<List<CastX>> {

        return try {
            Resource.Loading( data = true )
            val itemList = api.getCast( seriesId ).cast
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }
    }

}