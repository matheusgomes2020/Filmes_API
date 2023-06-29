package com.example.filmes.model

data class Series(
    val page: Int,
    val results: List<Serie>,
    val total_pages: Int,
    val total_results: Int
)