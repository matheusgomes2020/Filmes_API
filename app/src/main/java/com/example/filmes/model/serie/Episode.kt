package com.example.filmes.model.serie
import com.example.filmes.model.general.Cast
import com.example.filmes.model.general.Crew
import com.example.filmes.model.general.Images

data class Episode(
    val air_date: String,
    val crew: List<Crew>,
    val episode_number: Int,
    val guest_stars: List<Cast>,
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