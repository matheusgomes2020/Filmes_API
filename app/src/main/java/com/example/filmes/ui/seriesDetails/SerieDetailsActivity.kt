package com.example.filmes.ui.seriesDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.adapter.cast.CastAdapter
import com.example.filmes.adapter.season.SeasonAdapter
import com.example.filmes.adapter.season.SeasonClickListener
import com.example.filmes.databinding.ActivitySerieDetailBinding
import com.example.filmes.model.CastX
import com.example.filmes.model.SeasonX
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class SerieDetailsActivity : AppCompatActivity(), SeasonClickListener {

    private lateinit var binding: ActivitySerieDetailBinding
    private val viewModel: SeriesDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySerieDetailBinding.inflate(layoutInflater)
        setContentView( binding.root )

        val id = intent.getStringExtra("id")
        Log.d("UUU", "onCreate: " + id)
        viewModel.getSerieInfo(id!!)
        viewModel.getSeasonEpisodes( id!!, 1 )
        viewModel.getCast( id!! )
        observeSeries()
        observeEpisodes()
        observeCast()
        Log.d("UUU", "onCreate: " + id)
    }

    fun observeSeries() {

        try {
            viewModel.serieInfo.observe(this) {
                binding.seriesOverview.text = it.overview
                Log.d("UUU", "onCreate: " + it.id)
                binding.seriesTitle.text = it.name.toString()
                binding.seriesImageView.load("https://image.tmdb.org/t/p/w500" + it.poster_path)
                binding.textSeriesData.text = it.first_air_date
                binding.textSeriesDuration.text = it.episode_run_time.toString()

                var gen = ""
                it.genres.forEachIndexed { index, genres ->
                    gen += genres.name + "  "
                }
                binding.textSeriesGenres.text = gen

                when ( it.vote_average ) {
                    in 0.0..1.9 -> binding.texSeriesRating.text = "🌟⭐⭐⭐⭐"
                    in 2.0..3.9 -> binding.texSeriesRating.text = "🌟🌟⭐⭐⭐"
                    in 4.0..5.9 -> binding.texSeriesRating.text = "🌟🌟🌟⭐⭐"
                    in 6.0..7.9 -> binding.texSeriesRating.text = "🌟🌟🌟🌟⭐"
                    in 8.0..10.0 -> binding.texSeriesRating.text = "🌟🌟🌟🌟🌟"
                }
                setRecyclerView(it.seasons)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun observeEpisodes() {

        try {
            viewModel.seasonEpisodes.observe(this) {
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun observeCast() {

        try {
            viewModel.cast.observe(this) {
                setRecyclerViewCast( it )
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun setRecyclerView(lista: List<SeasonX>) {

        val mainActivity = this
        binding.recyclerSeasons.apply {
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
            adapter = SeasonAdapter(lista, mainActivity)
        }
    }

    private fun setRecyclerViewCast(lista: List<CastX>) {

        binding.recyclerCast.apply {
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
            adapter = CastAdapter( lista )
        }
    }

    override fun clickSeason(season: SeasonX) {
        Toast.makeText(this.applicationContext, season.season_number, Toast.LENGTH_LONG).show()
    }

}