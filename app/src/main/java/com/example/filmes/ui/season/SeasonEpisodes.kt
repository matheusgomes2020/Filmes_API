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
import com.example.filmes.model.SeasonX
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

            //binding.textTitle.text = "seasonX!!.toString()"
            binding.textViewTemporada.text = seasonX!!.season_number.toString() + " - Temporada"
            binding.textOverview.text = seasonX!!.overview

            if ( seasonX!!.poster_path == null ) binding.imageView6.load(R.drawable.padrao)
            else binding.imageView6.load("https://image.tmdb.org/t/p/w500" + seasonX!!.poster_path )

            seasonViewModel.loadSeason( serieId, seasonX!!.season_number )
            observe()
        }
    }

    fun observe(){

        try {
            seasonViewModel.season.observe(this){
                setRecyclerView(it.episodes)
            }

        }catch (e: Exception){
            e.printStackTrace()
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
        Toast.makeText( context, episode.name, Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSeasonEpisodesBinding.inflate(inflater,container,false)
        return binding.root
    }

}