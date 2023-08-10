package com.example.filmes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_tbl")
data class MovieRoom(
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "poster_path")
    val poster_path: String,
    @ColumnInfo(name = "title")
    val title: String,
    var userId: String = ""


)