package com.example.filmes.model

data class SimilarSeries(
    val page: Int,
    val results: List<Serie>,
    val total_pages: Int,
    val total_results: Int
)