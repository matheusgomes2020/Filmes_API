package com.example.filmes.utils

import com.example.filmes.model.MovieFirebase

data class MovieListFirebaseState(
    val data: List<MovieFirebase> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)