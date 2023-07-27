package com.example.filmes.model.general

data class Reviews(
    val page: Int,
    val results: List<Review>,
    val total_pages: Int,
    val total_results: Int
)