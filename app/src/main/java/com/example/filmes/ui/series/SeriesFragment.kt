package com.example.filmes.ui.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.adapter.Adapter
import com.example.filmes.databinding.FragmentSeriesBinding
import com.example.filmes.model.Serie
import com.example.filmes.views.SeriesView
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class SeriesFragment : Fragment() {

    private var _binding: FragmentSeriesBinding? = null
    private val binding get() = _binding!!
    private val seriesViewModel: SerieViewModel by viewModels()

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

        observe()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun observe() {

        try {
            seriesViewModel.airingTodaySeries.observe( viewLifecycleOwner ) {
                setRecyclerViewAiringToday( it )
            }
            seriesViewModel.onTheAirSeries.observe( viewLifecycleOwner ) {
                setRecyclerViewOnAir( it )
            }
            seriesViewModel.popularSeries.observe( viewLifecycleOwner ) {
                setRecyclerViewPopular( it )
            }
            seriesViewModel.topRatedSeries.observe( viewLifecycleOwner ) {
                setRecyclerViewTopRated( it )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun setRecyclerViewAiringToday(list: List<Serie> ) {
        binding.seriesRecyclerViewAiringToday.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = Adapter {
                SeriesView( it )
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerViewOnAir( list: List<Serie> ) {
        binding.serieRecyclerViewOnAir.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = Adapter {
                SeriesView( it )
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerViewPopular( list: List<Serie> ) {
        binding.serieRecyclerViewPopular.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = Adapter {
                SeriesView( it )
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerViewTopRated( list: List<Serie> ) {
        binding.serieRecyclerViewTopRated.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = Adapter {
                SeriesView( it )
            }.apply {
                items = list.toMutableList()
            }
        }
    }
}
