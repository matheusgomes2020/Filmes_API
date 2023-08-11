package com.example.filmes.model



data class SeriesFirebase(
    var id: String,
    val poster_path: String,
    val name: String,
    var userId: String = ""


)
{
    constructor(): this("", "", "")
}

fun main() {
    var seriesFirebase = SeriesFirebase()
}