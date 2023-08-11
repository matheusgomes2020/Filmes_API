package com.example.filmes.utils

import com.example.filmes.model.SeriesFirebase

data class SeriesListState(
    val data: List<SeriesFirebase> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)