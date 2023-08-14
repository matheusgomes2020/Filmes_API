package com.example.filmes.utils
import com.example.filmes.model.serie.Serie

data class SeriesList2State(
    val data: List<Serie> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)