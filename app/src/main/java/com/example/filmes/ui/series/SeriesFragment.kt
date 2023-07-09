package com.example.filmes.ui.series

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.adapter.serie.SerieAdapter
import com.example.filmes.adapter.serie.SerieClickListener
import com.example.filmes.databinding.FragmentSeriesBinding
import com.example.filmes.model.Serie
import com.example.filmes.ui.seriesDetails.SerieDetailsActivity
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

            binding.serieRecyclerViewLancamentos  .apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                adapter = SerieAdapter(it, mainActivity)

            }
        }

        serieViewModel.onTheAirSeries.observe(viewLifecycleOwner) {

            binding.serieRecyclerViewEmCartaz  .apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                adapter = SerieAdapter(it, mainActivity)

            }
        }

        serieViewModel.topRatedSeries.observe(viewLifecycleOwner) {

            binding.serieRecyclerViewMelhores  .apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                adapter = SerieAdapter(it, mainActivity)


            }
        }

        serieViewModel.airingTodaySeries.observe(viewLifecycleOwner) {


            binding.serieRecyclerViewEmAlta  .apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                adapter = SerieAdapter(it, mainActivity)

            }
        }





    }

        override fun clickSerie(serie: Serie) {

        Toast.makeText(this.context, serie.name.toString(), Toast.LENGTH_LONG).show()


        val id = serie.id.toString()
        val intent = Intent(this.context, SerieDetailsActivity::class.java).apply {
            putExtra("id", id)
            Log.d("SSSSS", "observeSeries: " + id)

        }
        startActivity(intent)


    }

}