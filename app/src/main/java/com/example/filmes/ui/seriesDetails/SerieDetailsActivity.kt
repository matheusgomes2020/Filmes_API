package com.example.filmes.ui.seriesDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import coil.load
import com.example.filmes.databinding.ActivitySerieDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class SerieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySerieDetailBinding
    private val viewModel: SeriesDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySerieDetailBinding.inflate(layoutInflater)
        setContentView( binding.root )

        val id = intent.getStringExtra("id")
        observeSeries()
        viewModel.getMovieInfo(id!!)

    }

    fun observeSeries() {

        try {
            viewModel.serieInfo.observe(this) {
                binding.movieOverview.text = it.toString()

                binding.movieTitle.text = it.name
                binding.imageView2.load("https://image.tmdb.org/t/p/w500" + it.poster_path)
                binding.textData.text = it.first_air_date
                binding.textDuration.text = it.episode_run_time.toString()

                var gen = ""
                it.genres.forEachIndexed { index, genres ->
                    gen += genres.name + "  "
                }
                binding.textGenres.text = gen

                when ( it.vote_average ) {
                    in 0.0..1.9 -> binding.texRating.text = "🌟⭐⭐⭐⭐"
                    in 2.0..3.9 -> binding.texRating.text = "🌟🌟⭐⭐⭐"
                    in 4.0..5.9 -> binding.texRating.text = "🌟🌟🌟⭐⭐"
                    in 6.0..7.9 -> binding.texRating.text = "🌟🌟🌟🌟⭐"
                    in 8.0..10.0 -> binding.texRating.text = "🌟🌟🌟🌟🌟"
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

}