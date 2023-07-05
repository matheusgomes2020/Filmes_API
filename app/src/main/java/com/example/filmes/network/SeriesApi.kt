package com.example.filmes.network

import com.example.filmes.model.Cast
import com.example.filmes.model.Seasons
import com.example.filmes.model.Serie
import com.example.filmes.model.Series
import com.example.filmes.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface SeriesApi {

    @GET("search/tv?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun searchSeries(

        @Query("query") query: String

    ): Series

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

    //https://api.themoviedb.org/3/tv/{series_id}/season/{season_number}

    //'https://api.themoviedb.org/3/tv/series_id/season/season_number?language=en-US

    //https://api.themoviedb.org/3/tv/12345/season/1?language=en-US1/1?language=en-US&api_key=0f5183b12ca04341d5f0f71d8bc698b5#

    @GET("tv/{series_id}/season/{season_number}?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getSeasonEpisodes(@Path("series_id") series_id: String, @Path("season_number") season_number: Int ) : Seasons

    //https://api.themoviedb.org/3/tv/114472/aggregate_credits?language=en-US'&api_key=0f5183b12ca04341d5f0f71d8bc698b5
    @GET("tv/{series_id}/aggregate_credits?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getCast(@Path("series_id") series_id: String ) : Cast



}