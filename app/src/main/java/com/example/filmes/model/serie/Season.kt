package com.example.filmes.model.serie

data class Season(
    val air_date: String,
    val episode_count: Int,
    val episodes: List<Episode>,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String,
    val season_number: Int,
    val vote_average: Double
)