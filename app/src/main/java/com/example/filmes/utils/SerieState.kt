package com.example.filmes.utils

import com.example.filmes.model.SeriesFirebase

data class SerieState(
    val data: SeriesFirebase? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
