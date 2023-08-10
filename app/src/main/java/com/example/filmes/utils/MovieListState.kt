package com.example.filmes.utils

import com.example.filmes.model.MovieF
import com.example.filmes.model.MovieRoom

data class MovieListState(
    val data: List<MovieF> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)