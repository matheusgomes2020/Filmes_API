package com.example.filmes.model.serie

data class Similar(
    val page: Int,
    val results: List<Serie>,
    val total_pages: Int,
    val total_results: Int
)