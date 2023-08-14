package com.example.filmes.utils

import com.example.filmes.model.serie.Season

data class SeasonState(
    val data: Season? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
