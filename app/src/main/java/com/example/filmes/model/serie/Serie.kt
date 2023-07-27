package com.example.filmes.model.serie

import com.example.filmes.model.general.Credits
import com.example.filmes.model.general.Genre
import com.example.filmes.model.general.Images
import com.example.filmes.model.general.ProductionCompany
import com.example.filmes.model.general.ProductionCountry
import com.example.filmes.model.general.Reviews
import com.example.filmes.model.general.SpokenLanguage
import com.example.filmes.model.general.Videos

data class Serie(
    val adult: Boolean,
    val aggregate_credits: Credits,
    val backdrop_path: String,
    val created_by: List<CreatedBy>,
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val images: Images,
    val in_production: Boolean,
    val languages: List<String>,
    val last_air_date: String,
    val last_episode_to_air: LastEpisodeToAir,
    val name: String,
    val networks: List<Network>,
    val next_episode_to_air: Any,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val reviews: Reviews,
    val seasons: List<Season>,
    val similar: Similar,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val type: String,
    val videos: Videos,
    val vote_average: Double,
    val vote_count: Int
)