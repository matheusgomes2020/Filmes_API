package com.example.filmes.model.general

import com.example.filmes.model.person.MovieCredits
import com.example.filmes.model.person.TvCredits

data class Cast(
    val adult: Boolean,
    val cast_id: Int,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val order: Int,
    val original_name: String,
    val popularity: Double,
    val profile_path: String,
    val roles: List<Role>,
    val total_episode_count: Int,
    val also_known_as: List<String>,
    val biography: String,
    val birthday: String,
    val deathday: Any,
    val homepage: Any,
    val images: Images,
    val imdb_id: String,
    val movie_credits: MovieCredits,
    val place_of_birth: String,
    val tv_credits: TvCredits
)