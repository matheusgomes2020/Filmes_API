package com.example.filmes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmes.model.MovieD

@Database(entities = [MovieD::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

}