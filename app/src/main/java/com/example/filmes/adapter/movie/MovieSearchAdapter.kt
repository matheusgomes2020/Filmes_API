package com.example.filmes.adapter.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.databinding.SearchMovieCellBinding
import com.example.filmes.model.Movie

class MovieSearchAdapter (

    private val lista: List<Movie>,
    private val clickListener: MovieClickListener

): RecyclerView.Adapter<MovieSearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchViewHolder {
        val from = LayoutInflater.from( parent.context )
        val bindind = SearchMovieCellBinding.inflate( from, parent, false )

        return MovieSearchViewHolder( parent.context, bindind, clickListener )

    }

    override fun onBindViewHolder(holder: MovieSearchViewHolder, position: Int) {
        holder.bindMovie( lista[position] )
    }

    override fun getItemCount(): Int = lista.size

}