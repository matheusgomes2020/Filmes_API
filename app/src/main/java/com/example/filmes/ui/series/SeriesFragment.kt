package com.example.filmes.ui.series

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
import com.example.filmes.adapter.Adapter
import com.example.filmes.databinding.FragmentSeriesBinding
import com.example.filmes.model.serie.Serie
import com.example.filmes.adapter.views.SeriesView
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

        observePopularSeries()
        observeAiringTodaySeries()
        observeOnTheAirSeries()
        observeRatedSeries()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observePopularSeries() {
        seriesViewModel.getPopularSeries()
        lifecycle.coroutineScope.launchWhenCreated {
            seriesViewModel.popularSeries.collect {
                if (it.isLoading) {}
                if (it.error.isNotBlank()) { }
                it.data?.let { _series ->
                    setRecyclerViewPopular(_series ).let {  Handler().postDelayed({
                        binding.sh3.visibility = View.GONE
                        binding.serieRecyclerViewPopular.visibility = View.VISIBLE
                        binding.sh3.stopShimmer()}, 1000) }
                }
            }
        }
    }
    private fun observeAiringTodaySeries() {
        seriesViewModel.getAiringTodaySeries()
        lifecycle.coroutineScope.launchWhenCreated {
            seriesViewModel.airingTodaySeries.collect {
                if (it.isLoading) { }
                if (it.error.isNotBlank()) { }
                it.data?.let { _series ->
                    setRecyclerViewAiringToday(_series ).let {
                        Handler().postDelayed({
                            binding.sh.visibility = View.GONE
                            binding.seriesRecyclerViewAiringToday.visibility = View.VISIBLE
                            binding.sh.stopShimmer()
                        }, 1000)
                    }
                }
            }
        }
    }

    private fun observeOnTheAirSeries() {
        seriesViewModel.getOnTheAirSeries()
        lifecycle.coroutineScope.launchWhenCreated {
            seriesViewModel.onTheAirSeries.collect {
                if (it.isLoading) {}
                if (it.error.isNotBlank()) { }
                it.data?.let { _series ->
                    setRecyclerViewOnAir(_series ).let {
                        Handler().postDelayed({
                            binding.sh2.visibility = View.GONE
                            binding.serieRecyclerViewOnAir.visibility = View.VISIBLE
                            binding.sh2.stopShimmer()}, 1000)
                    }
                }
            }
        }
    }

    private fun observeRatedSeries() {
        seriesViewModel.getRatedSeries()
        lifecycle.coroutineScope.launchWhenCreated {
            seriesViewModel.topRatedSeries.collect {
                if (it.isLoading) { }
                if (it.error.isNotBlank()) { }
                it.data?.let { _series ->
                    setRecyclerViewTopRated(_series ).let {
                        Handler().postDelayed({
                            binding.sh4.visibility = View.GONE
                            binding.serieRecyclerViewTopRated.visibility = View.VISIBLE
                            binding.sh4.stopShimmer() }, 1000)
                    }
                }
            }
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
