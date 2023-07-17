package com.example.filmes.ui.season

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.episodes.EpisodeClickListener
import com.example.filmes.adapter.episodes.EpisodesAdapter
import com.example.filmes.databinding.FragmentSeasonEpisodesBinding
import com.example.filmes.di.Episode2
import com.example.filmes.model.SeasonX
import com.example.filmes.model.Serie
import com.example.filmes.ui.episodes.Episode
import com.example.filmes.views.EpisodeView
import com.example.filmes.views.SeriesView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class SeasonEpisodes(var seasonX: SeasonX?, var serieId: String) :BottomSheetDialogFragment(),
    EpisodeClickListener {

    private lateinit var binding: FragmentSeasonEpisodesBinding
    private val seasonViewModel: SeasonViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if ( seasonX != null ) {
            binding.textViewTemporada.text = seasonX!!.season_number.toString() + " - Temporada"
            if ( seasonX!!.overview.isNullOrEmpty() ){
                binding.textOverview.visibility = View.GONE
            } else {
                binding.textOverview.text = seasonX!!.overview
            }
            if ( seasonX!!.poster_path.isNullOrEmpty() ) binding.imageView6.load(R.drawable.padrao)
            else binding.imageView6.load("https://image.tmdb.org/t/p/w500" + seasonX!!.poster_path )

            seasonViewModel.loadSeason( serieId, seasonX!!.season_number )
            observe()
        }
    }

    private fun observe(){

        try {
            seasonViewModel.season.observe(this){
                setRecyclerView(it.episodes)
            }

        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    private fun setRecyclerViewSimilar(list: List<com.example.filmes.di.Episode>) {

        binding.recyclerEpisodes.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                EpisodeView(it,serieId)
            }.apply {
                items = list.toMutableList()
            }
        }

    }
    private fun setRecyclerView( lista: List<com.example.filmes.di.Episode> ){

        val mainActivity = this
        binding.recyclerEpisodes.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            adapter = EpisodesAdapter(lista, mainActivity)
        }
    }

    override fun clickEpisode(episode: com.example.filmes.di.Episode) {
        Toast.makeText( context, episode.name + " | " + episode.season_number+ " | "+ episode.episode_number, Toast.LENGTH_LONG).show()
        Episode(serieId,episode.season_number, episode.episode_number).show(childFragmentManager, "seasonTag")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSeasonEpisodesBinding.inflate(inflater,container,false)
        return binding.root
    }

}