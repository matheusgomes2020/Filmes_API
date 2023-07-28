package com.example.filmes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.filmes.model.SerieRoom

@Dao
interface SerieDao {

    @Query("SELECT * from series_tbl")
    suspend fun getSeries(): List<SerieRoom>

    @Query("SELECT * from series_tbl where id =:id")
    suspend fun getSeriesById(id: String): SerieRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( series: SerieRoom )

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update( series: SerieRoom )

    @Query("DELETE from series_tbl")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteSeries( series: SerieRoom )

}