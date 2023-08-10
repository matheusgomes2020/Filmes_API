package com.example.filmes.utils

import com.example.filmes.model.MovieF
import com.example.filmes.model.MovieRoom

data class MovieState(
    val data: MovieF? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
