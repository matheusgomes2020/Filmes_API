package com.example.filmes.adapter.season

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
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


            binding.containerSeason.setOnClickListener {

                clickListener.clickSeason( season )

            }

        }

    }
