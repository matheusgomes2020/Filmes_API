package com.example.filmes.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.filmes.model.general.Credits
import com.example.filmes.model.general.Genre
import com.example.filmes.model.general.Images
import com.example.filmes.model.general.ProductionCompany
import com.example.filmes.model.general.ProductionCountry
import com.example.filmes.model.general.Reviews
import com.example.filmes.model.general.SpokenLanguage
import com.example.filmes.model.general.Videos
data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: BelongsToCollection,
    val budget: Int,
    val credits: Credits,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val images: Images,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val reviews: Reviews,
    val runtime: Int,
    val similar: Similar,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val videos: Videos,
    val vote_average: Double,
    val vote_count: Int
)