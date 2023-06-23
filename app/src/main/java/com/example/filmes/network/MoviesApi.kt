package com.example.filmes.network

import com.example.filmes.model.Movie
import com.example.filmes.model.Movies
import com.example.filmes.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface MoviesApi {

    @GET("search/movie?api_key=" + Constants.API_KEY)
    suspend fun searchMovies(

        @Query("query") query: String

    ): Movies

    @GET("movie/upcoming?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getUpcoming() : Movies


    @GET("movie/popular?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getPopularMovies() : Movies

    @GET("movie/top_rated?language=pt-BR&api_key=" + Constants.API_KEY)
    suspend fun getRatedMovies(): Movies

    @GET("movie/{movieID}?api_key=" + Constants.API_KEY)
    suspend fun  getMovieInfo(@Path("movieID") movieId: String) : Movie

    //https://api.themoviedb.org/3/movie/550?api_key=0f5183b12ca04341d5f0f71d8bc698b5
    @GET("movie/550?api_key=0f5183b12ca04341d5f0f71d8bc698b5")
    suspend fun  getMovieInfo2() : Movie

}