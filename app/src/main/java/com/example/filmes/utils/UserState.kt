package com.example.filmes.utils

import com.example.filmes.model.User

data class UserState(
    val data: User? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
