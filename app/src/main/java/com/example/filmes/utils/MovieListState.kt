package com.example.filmes.utils

import com.example.filmes.model.MovieFirebase

data class MovieListState(
    val data: List<MovieFirebase> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)