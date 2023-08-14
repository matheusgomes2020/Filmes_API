package com.example.filmes.ui.season

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.R
import com.example.filmes.databinding.FragmentSeasonEpisodesBinding
import com.example.filmes.model.serie.Episode
import com.example.filmes.model.serie.Season
import com.example.filmes.adapter.views.EpisodeView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class SeasonEpisodes(private var season: Season?, private var seriesId: String) :BottomSheetDialogFragment(){

    private lateinit var binding: FragmentSeasonEpisodesBinding
    private val seasonViewModel: SeasonViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if ( season != null ) {
            binding.textViewTemporada.text = season!!.season_number.toString() + " - Temporada"
            if ( season!!.overview.isNullOrEmpty() ){
                binding.textOverview.visibility = View.GONE
            } else {
                binding.textOverview.text = season!!.overview
            }
            if ( season!!.poster_path.isNullOrEmpty() ) binding.imageView6.load(R.drawable.padrao)
            else binding.imageView6.load("https://image.tmdb.org/t/p/w500" + season!!.poster_path )

            seasonViewModel.getSeasonEpisodes( seriesId, season!!.season_number )
            observe()
        }
    }

    private fun observe(){

        lifecycle.coroutineScope.launchWhenCreated {
            seasonViewModel.seasonEpisodesData.collect {
                if (it.isLoading) { }
                if (it.error.isNotBlank()) { }
                it.data?.let { _series ->
                    setRecyclerViewEpisodes(_series.episodes)
                }
            }
        }
    }

    private fun setRecyclerViewEpisodes(list: List<Episode>) {

        binding.recyclerEpisodes.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                EpisodeView(it,seriesId, childFragmentManager)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSeasonEpisodesBinding.inflate(inflater,container,false)
        return binding.root
    }
}