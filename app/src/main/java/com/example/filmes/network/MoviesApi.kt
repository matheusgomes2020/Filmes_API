package com.example.filmes.network

import com.example.filmes.model.Movies
import com.example.filmes.model.movie.Movie
import com.example.filmes.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface MoviesApi {

    @GET("search/movie?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun searchMovies(

        @Query("query") query: String

    ): Movies

    @GET("movie/upcoming?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getUpcoming() : Movies

    @GET("movie/popular?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getPopularMovies() : Movies

    @GET("movie/now_playing?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getNowPlaying() : Movies

    @GET("movie/top_rated?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getRatedMovies(): Movies

    @GET("movie/{movieID}?language=pt-BR&api_key=" + Constants.API_KEY + "&append_to_response=videos,images,reviews,similar,credits")
    suspend fun  getMovieInfo(@Path("movieID") movieId: String) : Movie


}