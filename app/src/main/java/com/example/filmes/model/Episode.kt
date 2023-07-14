package com.example.filmes.model

data class Episode(
    val air_date: String,
    val crew: List<Crew>,
    val episode_number: Int,
    val guest_stars: List<GuestStar>,
    val id: Int,
    val name: String,
    val overview: String,
    val production_code: String,
    val runtime: Int,
    val season_number: Int,
    val show_id: Int,
    val still_path: Any,
    val vote_average: Int,
    val vote_count: Int,
    val images: List<Still>
)