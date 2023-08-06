package com.example.filmes.di

data class Similar(
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
)