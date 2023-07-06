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
import com.example.filmes.adapter.movie.MovieAdapter
import com.example.filmes.adapter.movie.MovieClickListener
import com.example.filmes.databinding.FragmentMovieBinding
import com.example.filmes.model.Movie
import com.example.filmes.ui.movieDetails.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(), MovieClickListener {

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

        setRecyclerView()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRecyclerView() {

        val mainActivity = this
        movieViewModel.popularMovies.observe(viewLifecycleOwner) {

            binding.movieRecyclerViewEmAlta.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                adapter = MovieAdapter(it, mainActivity)
            }
        }

        movieViewModel.nowPlayingMovies.observe(viewLifecycleOwner) {

            binding.movieRecyclerViewEmCartaz.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                adapter = MovieAdapter(it, mainActivity)
            }
        }

        movieViewModel.upcomingMovies.observe(viewLifecycleOwner) {

            binding.movieRecyclerViewLancamentos.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                adapter = MovieAdapter(it, mainActivity)
            }
        }

        movieViewModel.ratedMovies.observe(viewLifecycleOwner) {

            binding.movieRecyclerViewMelhores.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                adapter = MovieAdapter(it, mainActivity)
            }
        }

    }

        override fun clickMovie(movie: Movie) {

            val id = movie.id.toString()
            val intent = Intent(this.context, MovieDetailsActivity::class.java).apply {
                putExtra("id", id)

            }
            startActivity(intent)
        }
    }

