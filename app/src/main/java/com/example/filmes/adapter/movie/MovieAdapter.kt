package com.example.filmes.adapter.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.databinding.MovieAnSeriesCellBinding
import com.example.filmes.model.Movie

class MovieAdapter (

    private val lista: List<Movie>,
    private val clickListener: MovieClickListener

    ): RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val from = LayoutInflater.from( parent.context )
        val bindind = MovieAnSeriesCellBinding.inflate( from, parent, false )

        return MovieViewHolder( parent.context, bindind, clickListener )

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie( lista[position] )
    }

    override fun getItemCount(): Int = lista.size

}