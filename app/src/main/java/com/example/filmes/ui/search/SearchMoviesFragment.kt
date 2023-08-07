package com.example.filmes.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.databinding.FragmentSearchMoviesBinding
import com.example.filmes.model.movie.Movie
import com.example.filmes.model.serie.Serie
import com.example.filmes.ui.searchSeries.SerieSearchViewModel
import com.example.filmes.adapter.views.SearchSeriesView
import com.example.filmes.adapter.views.SearchView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMoviesFragment(private var newText: String?, private var tipo: String?) : Fragment() {

    private var _binding: FragmentSearchMoviesBinding? =  null
    private val binding get() = _binding!!
    private val viewModel: SearchMoviesViewModel by viewModels()
    private val viewModelSeries: SerieSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        search(newText)
    }

    private fun search(query: String?){

        if (!query.isNullOrEmpty()) {

            if ( tipo == "movies" ) {
                viewModel.searchMovies( query )
                Toast.makeText( context, tipo, Toast.LENGTH_LONG).show()
            }else {
                viewModelSeries.searchSeries( query )
                Toast.makeText( context, tipo, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText( context, "Digite algo!!!", Toast.LENGTH_LONG).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe(){

        if ( tipo == "movies" ) {
            viewModel.popularMovies.observe( viewLifecycleOwner ) {
                setRecyclerViewSearch( it )
            }
        } else {
            viewModelSeries.series.observe(viewLifecycleOwner) {
                setRecyclerViewSearchSeries(it)
            }
        }
    }

    private fun setRecyclerViewSearch(list: List<Movie>) {

        binding.recyclerSearchMovies.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                SearchView(it)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerViewSearchSeries(list: List<Serie>) {

        binding.recyclerSearchMovies.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                SearchSeriesView(it)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

}