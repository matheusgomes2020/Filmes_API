package com.example.filmes.model


data class MovieFirebase(
    var id: String,
    val poster_path: String,
    val title: String,
    var userId: String = "",
    var idFirebase: String = ""


)
{
    constructor(): this("", "", "")
}

fun main() {
    var movieFirebase = MovieFirebase()
}


