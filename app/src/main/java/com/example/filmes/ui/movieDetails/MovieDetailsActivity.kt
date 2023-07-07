package com.example.filmes.ui.movieDetails

import android.R
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.adapter.cast.CastAdapter
import com.example.filmes.adapter.movie.MovieAdapter
import com.example.filmes.adapter.movie.MovieClickListener
import com.example.filmes.databinding.ActivityMovieDetailsBinding
import com.example.filmes.model.CastX
import com.example.filmes.model.Movie
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity(), MovieClickListener {

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
                binding.textViewDirecaoFilme.text = it.runtime.toString()

                for (i in it.credits.crew) {
                    if ( i.job == "Director" ) {
                        binding.textViewDirecaoFilme.text = i.name
                    }
                }

                var roteiro = ""

                for (i in it.credits.crew) {
                    if ( i.department == "Writing" ) {
                        roteiro += i.name + "\n"
                    }
                }

                binding.textViewRoteiro.text = roteiro

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

                if ( it.videos.results.isNullOrEmpty() ) {
                    binding.videoView.visibility = View.GONE
                } else {
                    binding.videoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            val videoId = it.videos.results[0].key
                            youTubePlayer.loadVideo(videoId, 0f)
                        }
                    })
                }

                setRecyclerViewSimilares(it.similar.results)


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

    private fun setRecyclerViewSimilares(lista: List<Movie>) {

        val mainActivity = this

        binding.recyclerMovieSimilars.apply {
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
            adapter = MovieAdapter( lista, mainActivity )
        }
    }

    override fun clickMovie(movie: Movie) {
        TODO("Not yet implemented")
    }

}










