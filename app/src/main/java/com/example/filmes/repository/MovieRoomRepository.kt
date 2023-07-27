package com.example.filmes.repository

import com.example.filmes.data.MovieDao
import com.example.filmes.model.MovieD
import javax.inject.Inject

class MovieRoomRepository @Inject constructor(private val movieDatabaseDao: MovieDao ) {

    suspend fun addMovie( movie: MovieD ) =  movieDatabaseDao.insert( movie )

    suspend fun getAllMovies(): List<MovieD> =  movieDatabaseDao.getMovies()

    suspend fun updateMovie( movie: MovieD ) =  movieDatabaseDao.update( movie )

    suspend fun deleteMovie( movie: MovieD ) =  movieDatabaseDao.deleteMovie( movie )

    suspend fun deleteAllMovies() =  movieDatabaseDao.deleteAll()




}