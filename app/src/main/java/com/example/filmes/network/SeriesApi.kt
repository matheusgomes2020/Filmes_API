package com.example.filmes.network

import com.example.filmes.model.Movie
import com.example.filmes.model.Movies
import com.example.filmes.model.Serie
import com.example.filmes.model.Series
import com.example.filmes.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface SeriesApi {

    //https://api.themoviedb.org/3/tv/popular?language=pt-BR&api_key=0f5183b12ca04341d5f0f71d8bc698b5
    @GET("tv/airing_today?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getAiringTodaySeries(): Series

    @GET("tv/popular?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getPopularSeries(): Series

    @GET("tv/top_rated?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getPopularTopRated(): Series

    @GET("tv/on_the_air?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getOnTheAirSeries(): Series



    //https://api.themoviedb.org/3/tv/12345?language=en-US&api_key=0f5183b12ca04341d5f0f71d8bc698b5#
    @GET("tv/{serieID}?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun  getSerieInfo(@Path("serieID") serieId: String) : Serie

}