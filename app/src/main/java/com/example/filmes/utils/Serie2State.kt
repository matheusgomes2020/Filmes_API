package com.example.filmes.utils
import com.example.filmes.model.serie.Serie

data class Serie2State(
    val data: Serie? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
