package com.example.filmes.ui.movieDetails

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.R
import com.example.filmes.databinding.ActivityMovieDetailsBinding
import com.example.filmes.model.MovieRoom
import com.example.filmes.model.general.Cast
import com.example.filmes.model.movie.Movie
import com.example.filmes.views.CastView
import com.example.filmes.views.MovieView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    var movieRoom: MovieRoom? = null
    var lista: List<MovieRoom> = emptyList()
    var favorito: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView( binding.root )

        val id = intent.getStringExtra("id")
        viewModel.getMovieInfo( id!! )
        observeMovies()
        observe()

        binding.imageView2.setOnClickListener {
            if (favorito) {
                Toast.makeText(applicationContext, movieRoom!!.title + " removido dos favoritos!!!", Toast.LENGTH_SHORT).show()
                viewModel.deleteMovie( movieRoom!! )
                binding.imageView2.setImageResource(R.drawable.ic_boomark)
                favorito = false
            } else {
                Toast.makeText(applicationContext, movieRoom!!.title + " salvo nos favoritos!!!", Toast.LENGTH_SHORT).show()
                viewModel.addMovie( movieRoom!! )
                binding.imageView2.setImageResource(R.drawable.ic_boomark_filled)
                favorito = true
            }

        }
    }

    private fun observeMovies() {

        try {
            var roteiro = ""
            var gen = ""

            viewModel.movieInfo.observe(this) {

                if ( !lista.isNullOrEmpty() ) {
                    for (i in lista ) {
                        if ( i.id == it.id ) {
                            binding.imageView2.setImageResource(R.drawable.ic_boomark_filled)
                            favorito = true
                            break
                        } else {
                            favorito = false
                        }
                    }
                } else {
                    favorito = false
                }

                binding.movieOverview.text = it.overview
                binding.movieTitle.text = it.title
                binding.textData.text = it.release_date
                binding.textDuration.text = it.runtime.toString()
                binding.textViewDirecaoFilme.text = it.runtime.toString()
                for (i in it.credits.crew) if ( i.job == "Director" ) binding.textViewDirecaoFilme.text = i.name
                for (i in it.credits.crew) if ( i.department == "Writing" ) roteiro += i.name + "\n"
                binding.textViewRoteiro.text = roteiro
                it.genres.forEachIndexed { index, genres ->
                    gen += genres.name + "  "
                }
                binding.textGenres.text = gen
                when ( it.vote_average ) {
                    in 0.0..1.9 -> binding.texRating.text = "⭐"
                    in 2.0..3.9 -> binding.texRating.text = "⭐⭐"
                    in 4.0..5.9 -> binding.texRating.text = "⭐⭐⭐"
                    in 6.0..7.9 -> binding.texRating.text = "⭐⭐⭐⭐"
                    in 8.0..10.0 -> binding.texRating.text = "⭐⭐⭐⭐⭐"
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

                setRecyclerViewSimilar(it.similar.results)
                setRecyclerViewCast(it.credits.cast)

                movieRoom = MovieRoom(
                    it.id,
                    it.poster_path ,
                    it.title
                )



            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun setRecyclerViewCast(list: List<Cast>) {

        binding.recyclerMoviecast.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                CastView(it, supportFragmentManager)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerViewSimilar(list: List<Movie>) {

        binding.recyclerMovieSimilars.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                MovieView(it)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun observe() {

        try {
            viewModel.movieList.observe(this) {
                lista = it
                 }
        }catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

}
