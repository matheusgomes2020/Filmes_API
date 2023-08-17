package com.example.filmes.utils

import com.example.filmes.model.MovieFirebase

data class MovieFirebaseState(
    val data: MovieFirebase? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
