package com.example.filmes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "series_tbl")
data class SeriesRoom(
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "poster_path")
    val poster_path: String,
    @ColumnInfo(name = "name")
    val name: String
)