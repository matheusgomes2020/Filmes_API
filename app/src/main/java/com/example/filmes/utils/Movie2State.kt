package com.example.filmes.utils

import com.example.filmes.model.movie.Movie


data class Movie2State(
    val data: Movie? = null,
    val error: String = "",
    val isLoading: Boolean = false
)