package com.example.filmes.ui.movieDetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.R
import com.example.filmes.databinding.ActivityMovieDetailsBinding
import com.example.filmes.model.MovieRoom
import com.example.filmes.model.general.Cast
import com.example.filmes.model.general.Review
import com.example.filmes.model.movie.Movie
import com.example.filmes.model.person.Profile
import com.example.filmes.adapter.views.CastView
import com.example.filmes.adapter.views.ImageView
import com.example.filmes.adapter.views.MovieView
import com.example.filmes.adapter.views.ReviewView
import com.example.filmes.model.MovieFirebase
import com.example.filmes.ui.login.AuthViewModel
import com.example.filmes.ui.perfil.FavoriteViewModel
import com.google.firebase.auth.FirebaseAuth
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private var movieFirebase: MovieFirebase? = null
    private var favorite: Boolean = true

    private val currentUser = FirebaseAuth.getInstance().currentUser

    private var listOfMovies = emptyList<MovieFirebase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView( binding.root )

       observeMoviesFirebase()

        val id = intent.getStringExtra("id")
        viewModel.getMovieInfo( id!! )
        observeMovie()



        binding.imageView2.setOnClickListener {

            if (favorite) {
                //Toast.makeText(applicationContext, movieFirebase!!.title + " removido dos favoritos!!!", Toast.LENGTH_SHORT).show()
                Toast.makeText(applicationContext, "Favorito!!!", Toast.LENGTH_SHORT).show()

                var movief = listOfMovies.filter { movie ->
                    movie.title == movieFirebase?.title
                }

                favoriteViewModel.deleteMovie(movief[0]!!)
                binding.imageView2.setImageResource(R.drawable.ic_boomark)
                favorite = false
            } else {
                Toast.makeText(applicationContext, movieFirebase!!.title + " salvo nos favoritos!!!", Toast.LENGTH_SHORT).show()
                favoriteViewModel.saveMovie(movieFirebase!!)
                binding.imageView2.setImageResource(R.drawable.ic_boomark_filled)
                favorite = true
            }

            observeMoviesFirebase()
            observeMovie()

        }
    }

    private fun observeMoviesFirebase() {
        favoriteViewModel.getMovies()
        lifecycle.coroutineScope.launchWhenCreated {
            favoriteViewModel.movieList.collect {
                if (it.isLoading) {
                }
                if (it.error.isNotBlank()) {
                }
                it.data?.let { _movie ->
                    listOfMovies = _movie.filter { movie ->
                        movie.userId == currentUser?.uid.toString() }
                    //setRecyclerFavoriteMovies(listOfMovies )
                }
            }
        }
    }

    private fun observeMovie() {

        try {
            var roteiro = ""
            var gen = ""

            viewModel.movieInfo.observe(this) {


                if ( !listOfMovies.isNullOrEmpty() ) {
                    for (i in listOfMovies ) {
                        if ( i.title == it.title ) {
                            binding.imageView2.setImageResource(R.drawable.ic_boomark_filled)
                            favorite = true
                            break
                        } else {
                            favorite = false
                        }
                    }
                } else {
                    favorite = false
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
                 if ( it.reviews.results.isNullOrEmpty() ) {
                   binding.recyclerMovieReviews.visibility = View.GONE
                  binding.textView443.visibility = View.GONE
                }
                else setRecyclerViewReviews( it.reviews.results )
               // if ( it.images.backdrops.isNullOrEmpty() ) {
                 //   binding.recyclerMovieImages.visibility = View.GONE
                 //   binding.textView443.visibility = View.GONE
                //}
                //else setRecyclerViewImages( it.images.backdrops!! )
                //setRecyclerViewImages(it.images.backdrops!!)


                movieFirebase = MovieFirebase(
                    it.id.toString(),
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

    private fun setRecyclerViewImages(list: List<Profile>) {

        binding.recyclerMovieReviews.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                ImageView(it, supportFragmentManager)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerViewReviews(list: List<Review>) {

        binding.recyclerMovieReviews.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                ReviewView(it, supportFragmentManager)
            }.apply {
                items = list.toMutableList()
            }
        }
    }


}
