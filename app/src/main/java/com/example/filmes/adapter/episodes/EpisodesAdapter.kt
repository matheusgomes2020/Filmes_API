package com.example.filmes.adapter.episodes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.databinding.EpisodeCellBinding
import com.example.filmes.model.Episode

class EpisodesAdapter (

    private val lista: List<Episode>,
    private val clickListener: EpisodeClickListener

): RecyclerView.Adapter<EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val from = LayoutInflater.from( parent.context )
        val binding = EpisodeCellBinding.inflate( from, parent, false )

        return EpisodeViewHolder( parent.context, binding, clickListener )

    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bindCast( lista[position] )
    }

    override fun getItemCount(): Int = lista.size

}