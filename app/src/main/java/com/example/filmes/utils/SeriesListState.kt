package com.example.filmes.utils
import com.example.filmes.model.serie.Serie

data class SeriesListState(
    val data: List<Serie> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)