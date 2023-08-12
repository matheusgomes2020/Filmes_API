package com.example.filmes.ui.filmes

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.MainActivity
import com.example.filmes.adapter.Adapter
import com.example.filmes.databinding.FragmentMovieBinding
import com.example.filmes.model.movie.Movie
import com.example.filmes.ui.search.SearchActivity
import com.example.filmes.ui.searchSeries.SerieSearchActivity
import com.example.filmes.adapter.views.MovieView
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUpcomingMovies()
        observeNowPlayingMovies()
        observeRatedMovies()
        observePopularMovies()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeUpcomingMovies() {
        movieViewModel.getUpcomingMovies()
        lifecycle.coroutineScope.launchWhenCreated {
            movieViewModel.movieListUpcoming.collect {
                if (it.isLoading) { }
                if (it.error.isNotBlank()) { }
                it.data?.let { _movie ->
                    setRecyclerViewUpcomingMovies(_movie )
                    Handler().postDelayed({
                    binding.sh.visibility = View.GONE
                        binding.textUpcoming.visibility = View.VISIBLE
                        binding.movieRecyclerViewLancamentos.visibility = View.VISIBLE
                        binding.sh.stopShimmer() }, 1000)
                }
            }
        }
    }

    private fun observeNowPlayingMovies() {
        movieViewModel.getNowPlayingMovies()
        lifecycle.coroutineScope.launchWhenCreated {
            movieViewModel.movieListNowPlaying.collect {
                if (it.isLoading) {}
                if (it.error.isNotBlank()) { }
                it.data?.let { _movie ->
                    setRecyclerViewNowPlayingMovies(_movie )
                   Handler().postDelayed({
                        binding.sh2.visibility = View.GONE
                        binding.textView.visibility = View.VISIBLE
                        binding.movieRecyclerViewEmCartaz.visibility = View.VISIBLE
                        binding.sh2.stopShimmer()}, 1000)
                }
            }
        }
    }

    private fun observeRatedMovies() {
        movieViewModel.getNowRatedMovies()
        lifecycle.coroutineScope.launchWhenCreated {
            movieViewModel.movieListRated.collect {
                if (it.isLoading) { }
                if (it.error.isNotBlank()) { }
                it.data?.let { _movie ->
                    setRecyclerViewRatedMovies(_movie )
                    Handler().postDelayed({
                        binding.sh4.visibility = View.GONE
                        binding.textView3.visibility = View.VISIBLE
                        binding.movieRecyclerViewMelhores.visibility = View.VISIBLE
                        binding.sh4.stopShimmer() }, 1000)
                }
            }
        }
    }

    private fun observePopularMovies() {
        movieViewModel.getNowPopularMovies()
        lifecycle.coroutineScope.launchWhenCreated {
            movieViewModel.movieListPopular.collect {
                if (it.isLoading) {}
                if (it.error.isNotBlank()) { }
                it.data?.let { _movie ->
                    setRecyclerViewPopular(_movie )
                    Handler().postDelayed({
                        binding.sh3.visibility = View.GONE
                        binding.textView2.visibility = View.VISIBLE
                        binding.movieRecyclerViewEmAlta.visibility = View.VISIBLE
                        binding.sh3.stopShimmer()}, 1000)
                }
            }
        }
    }


    private fun setRecyclerViewPopular( list: List<Movie> ) {
        binding.movieRecyclerViewEmAlta.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = Adapter {
                MovieView( it )
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerViewNowPlayingMovies( list: List<Movie> ) {
        binding.movieRecyclerViewEmCartaz.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = Adapter {
                MovieView( it )
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerViewUpcomingMovies( list: List<Movie> ) {
        binding.movieRecyclerViewLancamentos.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = Adapter {
                MovieView( it )
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerViewRatedMovies( list: List<Movie> ) {
        binding.movieRecyclerViewMelhores.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = Adapter {
                MovieView( it )
            }.apply {
                items = list.toMutableList()
            }
        }
    }
}

