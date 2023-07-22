package com.example.filmes.network


import com.example.filmes.model.Series
import com.example.filmes.model.general.Cast
import com.example.filmes.model.general.Credits
import com.example.filmes.model.serie.Episode
import com.example.filmes.model.serie.Season
import com.example.filmes.model.serie.Serie
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

    @GET("tv/airing_today?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getAiringTodaySeries(): Series

    @GET("tv/popular?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getPopularSeries(): Series

    @GET("tv/top_rated?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getPopularTopRated(): Series

    @GET("tv/on_the_air?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getOnTheAirSeries(): Series

    @GET("tv/{serieID}?language=pt-BR&api_key=" + Constants.API_KEY + "&append_to_response=videos,images,reviews,similar,aggregate_credits,episodes" )
    suspend fun  getSeriesInfo(@Path("serieID") serieId: String) : Serie

    @GET("tv/{series_id}/season/{season_number}?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getSeasonEpisodes(@Path("series_id") series_id: String, @Path("season_number") season_number: Int ) : Season


    @GET("tv/{series_id}/season/{season_number}/episode/{episode_number}?language=pt-BR&api_key=" + Constants.API_KEY + "&append_to_response=images")
    suspend fun getEpisodeInfo(@Path("series_id") series_id: String, @Path("season_number") season_number: Int, @Path("episode_number") episode_number: Int ) : Episode


}