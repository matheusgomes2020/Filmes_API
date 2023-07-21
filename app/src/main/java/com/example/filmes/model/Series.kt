package com.example.filmes.model

import com.example.filmes.model.serie.Serie

data class Series(
    val page: Int,
    val results: List<Serie>,
    val total_pages: Int,
    val total_results: Int
)