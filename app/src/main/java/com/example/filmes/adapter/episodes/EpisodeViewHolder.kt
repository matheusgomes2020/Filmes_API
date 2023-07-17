package com.example.filmes.adapter.episodes

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.R
import com.example.filmes.databinding.EpisodeCellBinding
import com.example.filmes.model.Episode

class EpisodeViewHolder (

    private val context: Context,
    private val binding: EpisodeCellBinding,
    private val clickListener: EpisodeClickListener

    ): RecyclerView.ViewHolder( binding.root ) {

        fun bindCast( episode: com.example.filmes.di.Episode ) {


            binding.textNameEpisode.text = episode.episode_number.toString() + " - " + episode.name
            binding.textOverviewEpisode.text = episode.overview

            if ( episode.still_path == null ){

                binding.imageEpisode.load( R.drawable.padrao )


            } else {

                binding.imageEpisode.load("https://image.tmdb.org/t/p/w500" + episode.still_path )
            }

            binding.episodeCellContainer.setOnClickListener {

               clickListener.clickEpisode( episode )

            }

        }

    }
