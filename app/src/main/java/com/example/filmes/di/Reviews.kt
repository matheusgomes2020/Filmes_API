package com.example.filmes.di

data class Reviews(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)