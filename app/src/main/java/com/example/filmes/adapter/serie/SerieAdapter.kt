package com.example.filmes.adapter.serie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.databinding.MovieCellBinding
import com.example.filmes.model.Movie
import com.example.filmes.model.Serie

class SerieAdapter (

    private val lista: List<Serie>,
    private val clickListener: SerieClickListener

    ): RecyclerView.Adapter<SerieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        val from = LayoutInflater.from( parent.context )
        val bindind = MovieCellBinding.inflate( from, parent, false )

        return SerieViewHolder( parent.context, bindind, clickListener )

    }

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        holder.bindMovie( lista[position] )
    }

    override fun getItemCount(): Int = lista.size

}