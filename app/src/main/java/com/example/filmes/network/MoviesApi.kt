package com.example.filmes.network

import com.example.filmes.model.Cast
import com.example.filmes.model.Movie
import com.example.filmes.model.Movies
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

    //now_playing?
    @GET("movie/now_playing?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getNowPlaying() : Movies

    @GET("movie/top_rated?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getRatedMovies(): Movies

    @GET("movie/{movieID}?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun  getMovieInfo(@Path("movieID") movieId: String) : Movie

    //https://api.themoviedb.org/3/movie/550?api_key=0f5183b12ca04341d5f0f71d8bc698b5
    //https://api.themoviedb.org/3/movie/603692/credits?language=en-US&api_key=0f5183b12ca04341d5f0f71d8bc698b5#

    @GET("movie/{movieID}/credits?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getCast(@Path("movieID") movieID: String ) : Cast


}