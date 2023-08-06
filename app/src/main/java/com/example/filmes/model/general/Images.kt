package com.example.filmes.model.general

import com.example.filmes.model.person.Profile

data class Images(
    val backdrops: List<Profile>,
    val logos: List<Image>,
    val posters: List<Image>,
    val profiles: List<Image>,
    val stills: List<Profile>
)