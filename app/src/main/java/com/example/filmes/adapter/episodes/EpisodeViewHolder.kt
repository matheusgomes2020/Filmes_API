package com.example.filmes.adapter.episodes

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.databinding.EpisodeCellBinding
import com.example.filmes.model.Episode

class EpisodeViewHolder (

    private val context: Context,
    private val binding: EpisodeCellBinding,
    private val clickListener: EpisodeClickListener

    ): RecyclerView.ViewHolder( binding.root ) {

        fun bindCast( episode: Episode ) {

            binding.imageEpisode.load("https://image.tmdb.org/t/p/w500" + episode.still_path )
            binding.text1.text = episode.name
            binding.text2.text = episode.episode_number.toString()
            binding.text3.text = episode.overview


            binding.episodeCellContainer.setOnClickListener {

               clickListener.clickEpisode( episode )

            }

        }

    }
