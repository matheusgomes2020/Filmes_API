package com.example.filmes.ui.seriesDetails


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.R
import com.example.filmes.databinding.ActivitySerieDetailBinding
import com.example.filmes.model.SerieRoom
import com.example.filmes.model.general.Cast
import com.example.filmes.model.serie.Season
import com.example.filmes.model.serie.Serie
import com.example.filmes.views.CastView
import com.example.filmes.views.SeasonView
import com.example.filmes.views.SeriesView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class SerieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySerieDetailBinding
    private val viewModel: SeriesDetailsViewModel by viewModels()
    var serieId = ""
    var nomeSerie = ""
    var seriesRoom: SerieRoom? = null
    var lista: List<SerieRoom> = emptyList()
    var favorito: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySerieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")
        serieId = id!!
        viewModel.getSerieInfo(id!!)
        viewModel.getSeasonEpisodes(id!!, 1)
        observeSeries()
        observe()

        binding.imageView8.setOnClickListener {
            if (favorito) {
                Toast.makeText(applicationContext, seriesRoom!!.name + " removida dos favoritos!!!", Toast.LENGTH_SHORT).show()
                viewModel.deleteSeries( seriesRoom!! )
                binding.imageView8.setImageResource(R.drawable.ic_favorite_border)
                favorito = false
            } else {
                Toast.makeText(applicationContext, seriesRoom!!.name + " salva nos favoritos!!!", Toast.LENGTH_SHORT).show()
                viewModel.addSeries( seriesRoom!! )
                binding.imageView8.setImageResource(R.drawable.ic_favorite)
                favorito = true
            }

        }

    }

    private fun observeSeries() {

        try {

            viewModel.seriesInfo.observe(this) {

                if ( !lista.isNullOrEmpty() ) {
                    for (i in lista ) {
                        if ( i.id == it.id ) {
                            binding.imageView8.setImageResource(R.drawable.ic_favorite)
                            favorito = true
                            break
                        } else {
                            favorito = false
                        }
                    }
                } else {
                    favorito = false
                }

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

                setRecyclerViewSeason(it.seasons)
                setRecyclerViewCast(it.aggregate_credits.cast)
                setRecyclerViewSimilar(it.similar.results)

                seriesRoom = SerieRoom(
                    it.id,
                    it.poster_path ,
                    it.name
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setRecyclerViewSeason(list: List<Season>) {

        binding.recyclerSeasons.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                SeasonView(it, serieId, (supportFragmentManager))
            }.apply {
                items = list.toMutableList()
            }
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

    private fun setRecyclerViewCast(list: List<Cast>) {

        binding.recyclerCast.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                CastView(it, supportFragmentManager)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun observe() {

        try {
            viewModel.seriesList.observe(this) {
                lista = it
            }
        }catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}