package com.example.filmes.data


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.filmes.model.MovieRoom


@Dao
interface MovieDao {

    @Query("SELECT * from movies_tbl")
    suspend fun getMovies(): List<MovieRoom>

    @Query("SELECT * from movies_tbl where id =:id")
    suspend fun getMovieById(id: String): MovieRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( movie: MovieRoom )

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update( movie: MovieRoom )

    @Query("DELETE from movies_tbl")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteMovie( movie: MovieRoom )

}