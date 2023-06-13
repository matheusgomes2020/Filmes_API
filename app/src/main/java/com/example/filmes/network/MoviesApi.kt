package com.example.filmes.network

import com.example.filmes.model.Movies
import com.example.filmes.utils.Constants
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface MoviesApi {

    @GET("movie/popular?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getPopularMovies() : Movies

    @GET("movie/top_rated?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getRatedMovies(): Movies

}