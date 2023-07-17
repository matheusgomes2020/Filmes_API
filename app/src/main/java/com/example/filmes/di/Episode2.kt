package com.example.filmes.di

import com.example.filmes.model.CastX

data class Episode2(
    val air_date: String,
    val crew: List<CrewX>,
    val episode_number: Int,
    val guest_stars: List<CastX>,
    val id: Int,
    val images: Images,
    val name: String,
    val overview: String,
    val production_code: String,
    val runtime: Int,
    val season_number: Int,
    val still_path: String,
    val vote_average: Double,
    val vote_count: Int
)