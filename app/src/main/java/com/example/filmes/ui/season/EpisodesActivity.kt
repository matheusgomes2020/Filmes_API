package com.example.filmes.ui.season

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.episodes.EpisodeClickListener
import com.example.filmes.adapter.episodes.EpisodesAdapter
import com.example.filmes.databinding.ActivityEpisodesBinding
import com.example.filmes.model.Episode
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class EpisodesActivity : AppCompatActivity(), EpisodeClickListener {

    private lateinit var binding: ActivityEpisodesBinding
    private val seasonViewModel: SeasonViewModel by viewModels()

    var poster = "nullll"
    var titulo = "nullll"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodesBinding.inflate(layoutInflater)
        setContentView( binding.root )


        val id = intent.getStringExtra("id")
        val nomeSerie = intent.getStringExtra("nome")
        val posterSerie = intent.getStringExtra("poster")
        poster = posterSerie!!
        titulo = nomeSerie!!
        val number = intent.getStringExtra("number")?.toInt()
        if (number != null) {
            seasonViewModel.loadSeason( id!!, number )
        }

            observe()

    }

    fun observe() {
        try {

            seasonViewModel.season.observe(this){

                setRecyclerView(it.episodes)

                binding.textTitle.text = titulo
                binding.textViewTemporada.text = it.season_number.toString() + " - Temporada"

                if (it.poster_path == null ){

                    binding.imageView6.load("https://image.tmdb.org/t/p/w500" + poster )


                } else {

                    binding.imageView6.load("https://image.tmdb.org/t/p/w500" + it.poster_path )


                }



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
        Toast.makeText( applicationContext, episode.name, Toast.LENGTH_LONG).show()

    }


}