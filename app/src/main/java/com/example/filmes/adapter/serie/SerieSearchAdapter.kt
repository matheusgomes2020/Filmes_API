package com.example.filmes.adapter.serie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.adapter.movie.MovieSearchViewHolder
import com.example.filmes.databinding.SearchMovieCellBinding
import com.example.filmes.model.Serie

class SerieSearchAdapter (

    private val lista: List<Serie>,
    private val clickListener: SerieClickListener

): RecyclerView.Adapter<SerieSaerchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieSaerchViewHolder {
        val from = LayoutInflater.from( parent.context )
        val bindind = SearchMovieCellBinding.inflate( from, parent, false )

        return SerieSaerchViewHolder( parent.context, bindind, clickListener )

    }

    override fun onBindViewHolder(holder: SerieSaerchViewHolder, position: Int) {
        holder.bindMovie( lista[position] )
    }

    override fun getItemCount(): Int = lista.size

}