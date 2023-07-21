package com.example.filmes.model.movie


data class Similar(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)