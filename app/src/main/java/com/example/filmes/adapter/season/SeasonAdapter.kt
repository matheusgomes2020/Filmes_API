package com.example.filmes.adapter.season

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.databinding.SeasonCellBinding
import com.example.filmes.model.SeasonX

class SeasonAdapter (

    private val lista: List<SeasonX>,
    private val clickListener: SeasonClickListener

): RecyclerView.Adapter<SeasonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val from = LayoutInflater.from( parent.context )
        val binding = SeasonCellBinding.inflate( from, parent, false )

        return SeasonViewHolder( parent.context, binding, clickListener )

    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        holder.bindSeason( lista[position] )
    }

    override fun getItemCount(): Int = lista.size

}