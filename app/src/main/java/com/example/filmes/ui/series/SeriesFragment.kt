package com.example.filmes.ui.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.adapter.movie.MovieAdapter
import com.example.filmes.adapter.serie.SerieAdapter
import com.example.filmes.adapter.serie.SerieClickListener
import com.example.filmes.databinding.FragmentSeriesBinding
import com.example.filmes.model.Movie
import com.example.filmes.model.Serie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesFragment : Fragment(), SerieClickListener {

    private var _binding: FragmentSeriesBinding? = null
    private val binding get() = _binding!!
    private val serieViewModel: SerieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSeriesBinding.inflate(inflater, container, false)
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
        serieViewModel.popularSeries.observe(viewLifecycleOwner) {

            binding.recyclerPopularSeries.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                adapter = SerieAdapter(it, mainActivity)

            }
        }
    }

        override fun clickMovie(serie: Serie) {

        Toast.makeText(this.context, serie.name.toString(), Toast.LENGTH_LONG).show()

        /*
        val id = movie.id.toString()
        val intent = Intent(this.context, MovieDetailsActivity::class.java).apply {
            putExtra("id", id)

        }
        startActivity(intent)

         */
    }

}