package com.example.filmes.data


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.filmes.model.MovieD


@Dao
interface MovieDao {

    @Query("SELECT * from movies_tbl")
    suspend fun getMovies(): List<MovieD>

    @Query("SELECT * from movies_tbl where id =:id")
    suspend fun getMovieById(id: String): MovieD

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( movie: MovieD )

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update( movie: MovieD )

    @Query("DELETE from movies_tbl")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteMovie( movie: MovieD )

}