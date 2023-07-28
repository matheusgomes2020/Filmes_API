package com.example.filmes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmes.model.MovieRoom
import com.example.filmes.model.SerieRoom

@Database(entities = [MovieRoom::class, SerieRoom::class], version = 2, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun seriesDao(): SerieDao

}