package com.example.filmes.model

data class Movie(

    val adult: Boolean,
    val backdrop_path: String,
    val genres: List<Genres>,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val runtime: Int,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int

)
