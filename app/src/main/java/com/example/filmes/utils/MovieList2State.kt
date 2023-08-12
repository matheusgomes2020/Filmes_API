package com.example.filmes.utils

import com.example.filmes.model.movie.Movie

data class MovieList2State(
    val data: List<Movie> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)