package com.example.filmes.repository

import android.util.Log
import com.example.filmes.data.MovieDao
import com.example.filmes.data.Resource
import com.example.filmes.model.MovieD
import javax.inject.Inject

class MRepository @Inject constructor( private val movieDatabaseDao: MovieDao ) {

    suspend fun addMovie( movie: MovieD ) =  movieDatabaseDao.insert( movie )


    suspend fun getAllMovies(): List<MovieD> {

            return movieDatabaseDao.getMovies()

    }


}