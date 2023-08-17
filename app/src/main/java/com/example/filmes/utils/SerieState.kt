package com.example.filmes.utils
import com.example.filmes.model.serie.Serie

data class SerieState(
    val data: Serie? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
