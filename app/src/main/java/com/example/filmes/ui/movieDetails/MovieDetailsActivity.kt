package com.example.filmes.ui.movieDetails

import android.R
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.adapter.cast.CastAdapter
import com.example.filmes.databinding.ActivityMovieDetailsBinding
import com.example.filmes.model.CastX
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView( binding.root )

        val id = intent.getStringExtra("id")
        viewModel.getMovieInfo( id!! )
        viewModel.getCast( id!! )
        observeMovies()
        observeCast()






        binding.movieTitle.setOnClickListener {



        }


    }

    fun observeMovies() {

        try {
            viewModel.movieInfo.observe(this) {
                binding.movieOverview.text = it.overview
                binding.movieTitle.text = it.title
                //Toast.makeText(this, it.toString(),)
                //binding.imageView2.load("https://image.tmdb.org/t/p/w500" + it.poster_path)
                binding.textData.text = it.release_date
                binding.textDuration.text = it.runtime.toString()

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

                binding.videoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        val videoId = it.videos.results[0].key
                        youTubePlayer.loadVideo(videoId, 0f)
                    }
                })


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

    private fun setRecyclerViewCast(lista: List<CastX>) {

        binding.recyclerMoviecast.apply {
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
            adapter = CastAdapter( lista )
        }
    }

}










