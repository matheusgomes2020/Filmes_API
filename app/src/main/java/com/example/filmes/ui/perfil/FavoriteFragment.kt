package com.example.filmes.ui.perfil

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.BaseViewHolder
import com.example.filmes.adapter.views.FavoriteMovieView
import com.example.filmes.adapter.views.FavoriteSeriesView
import com.example.filmes.databinding.FragmentProfileBinding
import com.example.filmes.model.MovieFirebase
import com.example.filmes.model.SeriesFirebase
import com.example.filmes.ui.movieDetails.MovieDetailsActivity
import com.example.filmes.ui.seriesDetails.SerieDetailsActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val viewModel: FavoriteViewModel by viewModels()

    private var listOfMovies = emptyList<MovieFirebase>()
    private var listOfSeries = emptyList<SeriesFirebase>()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMoviesFirebase()
        observeSeriesFirebase()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun observeMoviesFirebase() {
        viewModel.getMovies()
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.movieList.collect {
                if (it.isLoading) {
                }
                if (it.error.isNotBlank()) {
                }
                it.data?.let { _movie ->
                    listOfMovies = _movie.filter { movie ->
                        movie.userId == currentUser?.uid.toString() }
                    setRecyclerFavoriteMovies(listOfMovies ).let {
                        Handler().postDelayed({
                            binding.sh.visibility = View.GONE
                            binding.recyclerFavoriteMovies.visibility = View.VISIBLE
                            binding.sh.stopShimmer()
                        }, 1000)
                    }
                }
            }
        }
    }

    fun observeSeriesFirebase() {
        viewModel.getSeries()
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.seriesList.collect {
                if (it.isLoading) {
                }
                if (it.error.isNotBlank()) {
                }
                it.data?.let { _series ->
                    listOfSeries = _series.filter { series ->
                        series.userId == currentUser?.uid.toString() }
                    setRecyclerFavoriteSeries(listOfSeries ).let {
                        Handler().postDelayed({
                            binding.sh2.visibility = View.GONE
                            binding.recyclerFavoriteSeries.visibility = View.VISIBLE
                            binding.sh2.stopShimmer()
                        }, 1000)
                    }
                }
            }
        }
    }

    private fun setRecyclerFavoriteMovies(list: List<MovieFirebase>) {

        binding.recyclerFavoriteMovies.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                FavoriteMovieView(it, viewModel, this@FavoriteFragment)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerFavoriteSeries(list: List<SeriesFirebase>) {

        binding.recyclerFavoriteSeries.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                FavoriteSeriesView(it, viewModel, this@FavoriteFragment)
            }.apply {
                items = list.toMutableList()
            }
        }
    }
}


