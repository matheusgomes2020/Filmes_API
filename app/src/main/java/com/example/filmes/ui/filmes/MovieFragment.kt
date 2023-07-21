package com.example.filmes.ui.filmes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.adapter.Adapter
import com.example.filmes.databinding.FragmentMovieBinding
import com.example.filmes.model.movie.Movie
import com.example.filmes.views.MovieView
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

        observe()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {

        try {
            movieViewModel.popularMovies.observe( viewLifecycleOwner ) {
                setRecyclerViewPopular( it )
            }
            movieViewModel.nowPlayingMovies.observe( viewLifecycleOwner ) {
                setRecyclerViewNowPlayingMovies( it )
            }
            movieViewModel.upcomingMovies.observe( viewLifecycleOwner ) {
                setRecyclerViewUpcomingMovies( it )
            }
            movieViewModel.ratedMovies.observe( viewLifecycleOwner ) {
                setRecyclerViewRatedMovies( it )
            }
        } catch (e: Exception) {
            e.printStackTrace()
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

