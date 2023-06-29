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
import com.example.filmes.adapter.MovieAdapter
import com.example.filmes.adapter.MovieClickListener
import com.example.filmes.databinding.FragmentMovieBinding
import com.example.filmes.model.Movie
import com.example.filmes.ui.movieDetails.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(), MovieClickListener {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MovieViewModel by viewModels()

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
        mainViewModel.popularMovies.observe(viewLifecycleOwner) {

            binding.movieRecyclerViewEmAlta.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                adapter = MovieAdapter(it, mainActivity)

                binding.shimmer.stopShimmer()
                binding.shimmer.visibility = View.GONE
                binding.movieRecyclerViewEmAlta.visibility = View.VISIBLE
            }
        }

        mainViewModel.nowPlayingovies.observe(viewLifecycleOwner) {

            binding.movieRecyclerViewEmCartaz.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                adapter = MovieAdapter(it, mainActivity)
                binding.shimmer2.stopShimmer()
                binding.shimmer2.visibility = View.GONE
                binding.movieRecyclerViewEmCartaz.visibility = View.VISIBLE
            }
        }

        mainViewModel.upcomingMovies.observe(viewLifecycleOwner) {

            binding.movieRecyclerViewLancamentos.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                adapter = MovieAdapter(it, mainActivity)
                binding.shimmer3.stopShimmer()
                binding.shimmer3.visibility = View.GONE
                binding.movieRecyclerViewLancamentos.visibility = View.VISIBLE
            }
        }

        mainViewModel.ratedMovies.observe(viewLifecycleOwner) {

            binding.movieRecyclerViewMelhores.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                adapter = MovieAdapter(it, mainActivity)

                binding.shimmer4.stopShimmer()
                binding.shimmer4.visibility = View.GONE
                binding.movieRecyclerViewMelhores.visibility = View.VISIBLE
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

