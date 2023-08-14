package com.example.filmes.ui.seriesDetails


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.R
import com.example.filmes.databinding.ActivitySerieDetailBinding
import com.example.filmes.model.SeriesFirebase
import com.example.filmes.model.general.Cast
import com.example.filmes.model.general.Review
import com.example.filmes.model.serie.Season
import com.example.filmes.model.serie.Serie
import com.example.filmes.adapter.views.CastView
import com.example.filmes.adapter.views.ReviewView
import com.example.filmes.adapter.views.SeasonView
import com.example.filmes.adapter.views.SeriesView
import com.example.filmes.ui.perfil.FavoriteViewModel
import com.google.firebase.auth.FirebaseAuth
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class SerieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySerieDetailBinding
    private val viewModel: SeriesDetailsViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private var seriesId = ""
    private var nomeSeries = ""
    private var seriesFirebase: SeriesFirebase? = null
    private var favorite: Boolean = true
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private var listOfSeries = emptyList<SeriesFirebase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySerieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeSeriesFirebase()

        val id = intent.getStringExtra("id")
        seriesId = id!!
        viewModel.getSerieInfo(id!!)
        observeSeries()

        binding.imageView8.setOnClickListener {

            if (favorite) {
                var favoriteSeries = listOfSeries.filter { series ->
                    series.name == seriesFirebase?.name }
                favoriteViewModel.deleteSeries(favoriteSeries[0]!!).let {
                    binding.imageView8.setImageResource(R.drawable.ic_boomark)
                    favorite = false
                    Toast.makeText(applicationContext, seriesFirebase!!.name + ", removida nos favoritos!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, seriesFirebase!!.name + ", salva nos favoritos!", Toast.LENGTH_SHORT).show()
                favoriteViewModel.saveSeries(seriesFirebase!!).let {
                    binding.imageView8.setImageResource(R.drawable.ic_boomark_filled)
                    favorite = true
                }
            }

            observeSeriesFirebase()
        }
    }

    private fun observeSeriesFirebase() {
        favoriteViewModel.getSeries()
        lifecycle.coroutineScope.launchWhenCreated {
            favoriteViewModel.seriesList.collect {
                if (it.isLoading) {
                }
                if (it.error.isNotBlank()) {
                }
                it.data?.let { _series ->
                    listOfSeries = _series.filter { series ->
                        series.userId == currentUser?.uid.toString() }
                }
            }
        }
    }

    private fun observeSeries() {

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.seriesData.collect {
                if (it.isLoading) {
                    binding.constSeries.visibility = View.INVISIBLE
                }
                if (it.error.isNotBlank()) {
                }
                it.data?.let { _series ->
                    if ( !listOfSeries.isNullOrEmpty() ) {
                        for (i in listOfSeries ) {
                            if ( i.name == _series.name ) {
                                binding.imageView8.setImageResource(R.drawable.ic_boomark_filled)
                                favorite = true
                                break
                            } else {
                                favorite = false
                            }
                        }
                    } else {
                        favorite = false
                    }

                    binding.constSeries.visibility = View.VISIBLE

                    nomeSeries = _series.name
                    binding.seriesOverview.text = _series.overview
                    binding.seriesTitle.text = _series.name
                    binding.textSeriesData.text = _series.first_air_date
                    binding.textSeriesDuration.text = _series.episode_run_time.toString()

                    if (_series.number_of_seasons == 1) binding.textSeasons.text = "1 Temporada"
                    else binding.textSeasons.text = _series.number_of_seasons.toString() + " Temporadas"

                    if (_series.episode_run_time.isNullOrEmpty()) {
                        binding.textSeriesDuration.visibility = View.GONE
                        binding.imageView4.visibility = View.GONE
                    } else {
                        binding.textSeriesDuration.text = _series.episode_run_time.toString()
                    }

                    if (_series.created_by.isNullOrEmpty()) {
                        binding.textView13.visibility = View.GONE
                        binding.textViewSerieDirecao.visibility = View.GONE
                    } else {
                        var roteiro = ""

                        for (i in _series.created_by) {
                            roteiro += i.name + "\n"
                        }
                        binding.textViewSerieDirecao.text = roteiro
                    }

                    var gen = ""
                    _series.genres.forEachIndexed { index, genres ->
                        gen += genres.name + "  "
                    }
                    binding.textSeriesGenres.text = gen

                    when (_series.vote_average) {
                        in 0.0..1.9 -> binding.texSeriesRating.text = "⭐"
                        in 2.0..3.9 -> binding.texSeriesRating.text = "⭐⭐"
                        in 4.0..5.9 -> binding.texSeriesRating.text = "⭐⭐⭐"
                        in 6.0..7.9 -> binding.texSeriesRating.text = "⭐⭐⭐⭐"
                        in 8.0..10.0 -> binding.texSeriesRating.text = "⭐⭐⭐⭐⭐"
                    }

                    if (_series.videos.results.isNullOrEmpty()) {
                        binding.seriesImageView.visibility = View.GONE
                    } else {
                        binding.seriesImageView.addYouTubePlayerListener(object :
                            AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                val videoId = _series.videos.results[0].key
                                youTubePlayer.loadVideo(videoId, 0f)
                            }
                        })
                    }

                    setRecyclerViewSeason(_series.seasons)
                    setRecyclerViewCast(_series.aggregate_credits.cast)
                    setRecyclerViewSimilar(_series.similar.results)

                    if ( _series.reviews.results.isNullOrEmpty() ) {
                        binding.recyclerSeriesReviews.visibility = View.GONE
                        binding.textView443.visibility = View.GONE
                    }
                    else setRecyclerViewReviews( _series.reviews.results )

                    seriesFirebase = SeriesFirebase(
                        _series.id.toString(),
                        _series.poster_path ,
                        _series.name
                    )

                }
            }
        }
    }

    private fun setRecyclerViewSeason(list: List<Season>) {

        binding.recyclerSeasons.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                SeasonView(it, seriesId, (supportFragmentManager))
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

    private fun setRecyclerViewReviews(list: List<Review>) {

        binding.recyclerSeriesReviews.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                ReviewView(it, supportFragmentManager)
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
}