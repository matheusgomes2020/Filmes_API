package com.example.filmes.model


data class MovieF(
    var id: String,
    val poster_path: String,
    val title: String,
    var userId: String = ""


)
{
    constructor(): this("", "", "")
}

fun main() {
    var movieF = MovieF()
}


