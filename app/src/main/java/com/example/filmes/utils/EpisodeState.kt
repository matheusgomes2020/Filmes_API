package com.example.filmes.utils
import com.example.filmes.model.serie.Episode

data class EpisodeState(
    val data: Episode? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
