package com.example.filmes.utils

import com.example.filmes.model.MovieFirebase

data class MovieState(
    val data: MovieFirebase? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
