package com.example.filmes.model

import com.example.filmes.model.movie.Movie

data class Movies(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
