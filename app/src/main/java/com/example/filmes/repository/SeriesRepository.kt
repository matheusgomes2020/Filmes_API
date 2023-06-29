package com.example.filmes.repository

import com.example.filmes.data.Resource
import com.example.filmes.model.Serie
import com.example.filmes.network.SeriesApi
import javax.inject.Inject

class SeriesRepository @Inject constructor( private val api: SeriesApi) {

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

}