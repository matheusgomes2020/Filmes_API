package com.example.filmes.ui.seriesDetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.adapter.cast.CastClickListener
import com.example.filmes.adapter.season.SeasonAdapter
import com.example.filmes.adapter.season.SeasonClickListener
import com.example.filmes.adapter.serie.SerieClickListener
import com.example.filmes.databinding.ActivitySerieDetailBinding
import com.example.filmes.model.CastX
import com.example.filmes.model.SeasonX
import com.example.filmes.model.Serie
import com.example.filmes.views.CastView
import com.example.filmes.ui.season.SeasonEpisodes
import com.example.filmes.views.SeriesView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class SerieDetailsActivity : AppCompatActivity(), SeasonClickListener, CastClickListener, SerieClickListener {

    private lateinit var binding: ActivitySerieDetailBinding
    private val viewModel: SeriesDetailsViewModel by viewModels()
    var serieId = ""
    var nomeSerie = ""
    var poster = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySerieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")
        serieId = id!!
        viewModel.getSerieInfo(id!!)
        viewModel.getSeasonEpisodes(id!!, 1)
        viewModel.getCast(id!!)
        observeSeries()
        observeCast()

    }

    private fun observeSeries() {

        try {
            viewModel.serieInfo.observe(this) {
                nomeSerie = it.name
                binding.seriesOverview.text = it.overview
                binding.seriesTitle.text = it.name
                binding.textSeriesData.text = it.first_air_date
                binding.textSeriesDuration.text = it.episode_run_time.toString()

                if (it.number_of_seasons == 1) binding.textSeasons.text = "1 Temporada"
                else binding.textSeasons.text = it.number_of_seasons.toString() + " Temporadas"

                if (it.episode_run_time.isNullOrEmpty()) {
                    binding.textSeriesDuration.visibility = View.GONE
                    binding.imageView4.visibility = View.GONE
                } else {
                    binding.textSeriesDuration.text = it.episode_run_time.toString()
                }

                if (it.created_by.isNullOrEmpty()) {
                    binding.textViewSerieDirecao.text = "Null"
                } else {
                    var roteiro = ""

                    for (i in it.created_by) {
                        roteiro += i.name + "\n"
                    }
                    binding.textViewSerieDirecao.text = roteiro
                }

                var gen = ""
                it.genres.forEachIndexed { index, genres ->
                    gen += genres.name + "  "
                }
                binding.textSeriesGenres.text = gen

                when (it.vote_average) {
                    in 0.0..1.9 -> binding.texSeriesRating.text = "⭐"
                    in 2.0..3.9 -> binding.texSeriesRating.text = "⭐⭐"
                    in 4.0..5.9 -> binding.texSeriesRating.text = "⭐⭐⭐"
                    in 6.0..7.9 -> binding.texSeriesRating.text = "⭐⭐⭐⭐"
                    in 8.0..10.0 -> binding.texSeriesRating.text = "⭐⭐⭐⭐⭐"
                }

                if (it.videos.results.isNullOrEmpty()) {
                    binding.seriesImageView.visibility = View.GONE
                } else {
                    binding.seriesImageView.addYouTubePlayerListener(object :
                        AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            val videoId = it.videos.results[0].key
                            youTubePlayer.loadVideo(videoId, 0f)
                        }
                    })
                }

                setRecyclerView(it.seasons)
                setRecyclerViewSimilar(it.similar.results)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun observeCast() {

        try {
            viewModel.cast.observe(this) {
                setRecyclerViewCast(it)
            }
        } catch (e: Exception) {
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

    private fun setRecyclerViewSimilar(list: List<Serie>) {

        binding.recyclerSeriresSimilares.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                SeriesView(it)
            }.apply {
                items = list.toMutableList()
            }
        }

    }

    private fun setRecyclerViewCast(list: List<CastX>) {

        binding.recyclerCast.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                CastView(it)
            }.apply {
                items = list.toMutableList()
            }
        }
    }


    override fun clickSeason(season: SeasonX) {

        SeasonEpisodes(season, serieId).show(supportFragmentManager, "seasonTag")
    }

    override fun clickCast(cast: CastX) {
        Toast.makeText(applicationContext, cast.name, Toast.LENGTH_SHORT).show()
    }



    override fun clickSerie(serie: Serie) {
        val id = serie.id.toString()
        val intent = Intent( applicationContext, SerieDetailsActivity::class.java ).apply {
            putExtra("id", serie.id.toString() )
        }
        startActivity(intent)
    }

}