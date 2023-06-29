package com.example.filmes.network

import com.example.filmes.model.Movies
import com.example.filmes.model.Series
import com.example.filmes.utils.Constants
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface SeriesApi {

    //https://api.themoviedb.org/3/tv/popular?language=pt-BR&api_key=0f5183b12ca04341d5f0f71d8bc698b5
    @GET("tv/popular?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getPopularSeries(): Series
}