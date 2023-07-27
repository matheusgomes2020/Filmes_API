package com.example.filmes.data


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmes.model.MovieD


@Dao
interface MovieDao {

    @Query("SELECT * from movies_tbl")
    suspend fun getMovies(): List<MovieD>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( movie: MovieD )

}