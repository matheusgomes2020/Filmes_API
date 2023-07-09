package com.example.filmes.adapter.season

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.R
import com.example.filmes.databinding.SeasonCellBinding
import com.example.filmes.model.SeasonX

class SeasonViewHolder (

    private val context: Context,
    private val binding: SeasonCellBinding,
    private val clickListener: SeasonClickListener

    ): RecyclerView.ViewHolder( binding.root ) {

        fun bindSeason( season: SeasonX ) {

            binding.textViewNTemporada.text = season.season_number.toString() + " - Temporada"
            binding.textViewQtdEpisodios.text = season.episode_count.toString() + " - Episódios"

            if ( season.poster_path == null ){

                binding.imageView2.load( R.drawable.padrao )


            } else {

                binding.imageView2.load("https://image.tmdb.org/t/p/w500" + season.poster_path)

            }


            binding.containerSeason.setOnClickListener {

                clickListener.clickSeason( season )

            }

        }

    }
