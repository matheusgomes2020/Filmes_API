package com.example.filmes.ui.seriesDetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.adapter.cast.CastAdapter
import com.example.filmes.adapter.cast.CastClickListener
import com.example.filmes.adapter.season.SeasonAdapter
import com.example.filmes.adapter.season.SeasonClickListener
import com.example.filmes.adapter.serie.SerieAdapter
import com.example.filmes.adapter.serie.SerieClickListener
import com.example.filmes.databinding.ActivitySerieDetailBinding
import com.example.filmes.model.CastX
import com.example.filmes.model.SeasonX
import com.example.filmes.model.Serie
import com.example.filmes.ui.season.EpisodesActivity
import com.example.filmes.ui.season.SeasonFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class SerieDetailsActivity : AppCompatActivity(), SeasonClickListener, CastClickListener, SerieClickListener {

    private lateinit var binding: ActivitySerieDetailBinding
    private val viewModel: SeriesDetailsViewModel by viewModels()
    var id1 =  ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySerieDetailBinding.inflate(layoutInflater)
        setContentView( binding.root )

        val id = intent.getStringExtra("id")
        id1 = id!!
        viewModel.getSerieInfo(id!!)
        viewModel.getSeasonEpisodes( id!!, 1 )
        viewModel.getCast( id!! )
        observeSeries()
        observeEpisodes()
        observeCast()
    }

    fun observeSeries() {

        try {
            viewModel.serieInfo.observe(this) {
                binding.seriesOverview.text = it.overview
                binding.seriesTitle.text = it.name
                //binding.seriesImageView.load("https://image.tmdb.org/t/p/w500" + it.poster_path)
                binding.textSeriesData.text = it.first_air_date
                binding.textSeriesDuration.text = it.episode_run_time.toString()

                if ( it.aggregate_credits == null ) {
                    binding.textViewSerieDirecao.text = "Null"
                    binding.textViewSerieRoteiro.text = "Null"
                } else {
                    for (i in it.aggregate_credits.crew) {
                        if ( i.job == "Director" ) {
                            binding.textViewSerieDirecao.text = i.name
                        }
                    }

                    var roteiro = ""

                    for (i in it.aggregate_credits.crew) {
                        if ( i.department == "Writing" ) {
                            roteiro += i.name + "\n"
                        }
                    }

                    binding.textViewSerieRoteiro.text = roteiro
                }



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

                if ( it.videos.results.isNullOrEmpty() ) {
                    binding.seriesImageView.visibility = View.GONE
                } else {
                    binding.seriesImageView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            val videoId = it.videos.results[0].key
                            youTubePlayer.loadVideo(videoId, 0f)
                        }
                    })
                }

                setRecyclerView(it.seasons)

                setRecyclerViewSimilares( it.similar.results )
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

    private fun setRecyclerViewSimilares(lista: List<Serie>) {

        val mainActivity = this

        binding.recyclerSeriresSimilares.apply {
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
            adapter = SerieAdapter( lista, mainActivity )
        }
    }
    private fun setRecyclerViewCast(lista: List<CastX>) {

        val mainActivity = this

        binding.recyclerCast.apply {
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
            adapter = CastAdapter( lista, mainActivity )
        }

    }

    override fun clickSeason(season: SeasonX) {
        Toast.makeText(this.applicationContext, season.toString(), Toast.LENGTH_LONG).show()
        val intent = Intent( applicationContext, EpisodesActivity::class.java ).apply {
            putExtra("number", season.season_number.toString() )
            putExtra("id", id1 )

        }
        startActivity(intent)
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