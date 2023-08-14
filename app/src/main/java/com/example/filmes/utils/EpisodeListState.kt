package com.example.filmes.utils

import com.example.filmes.model.serie.Episode

data class EpisodeListState(
    val data: List<Episode> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)