package com.example.filmes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmes.model.MovieRoom
import com.example.filmes.model.SeriesFirebase
import com.example.filmes.model.SeriesRoom

@Database(entities = [MovieRoom::class, SeriesRoom::class], version = 4, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun seriesDao(): SerieDao

}