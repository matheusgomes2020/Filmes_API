package com.example.filmes.model.general

data class Crew(
    val adult: Boolean,
    val department: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val jobs: List<Job>,
    val known_for_department: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String,
    val total_episode_count: Int
)