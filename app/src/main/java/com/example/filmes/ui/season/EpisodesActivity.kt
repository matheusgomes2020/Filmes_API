package com.example.filmes.ui.season

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.R
import com.example.filmes.adapter.episodes.EpisodeClickListener
import com.example.filmes.adapter.episodes.EpisodesAdapter
import com.example.filmes.databinding.ActivityEpisodesBinding
import com.example.filmes.databinding.ActivitySerieDetailBinding
import com.example.filmes.model.Episode
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class EpisodesActivity : AppCompatActivity(), EpisodeClickListener {

    private lateinit var binding: ActivityEpisodesBinding
    private val seasonViewModel: SeasonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodesBinding.inflate(layoutInflater)
        setContentView( binding.root )


        val id = intent.getStringExtra("id")
        val number = intent.getStringExtra("number")?.toInt()
        if (number != null) {
            seasonViewModel.loadSeason( id!!, number )
        }

        binding.textNumberSeason.text = number.toString()


            observe()



    }

    fun observe() {
        try {

            seasonViewModel.season.observe(this){

                setRecyclerView(it)


            }

        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    private fun setRecyclerView( lista: List<Episode> ){

        val mainActivity = this


            binding.recyclerEpisodes.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
                adapter = EpisodesAdapter(lista, mainActivity)
            }


    }

    override fun clickEpisode(episode: Episode) {
        Toast.makeText( applicationContext, episode.name, Toast.LENGTH_LONG).show()

    }


}