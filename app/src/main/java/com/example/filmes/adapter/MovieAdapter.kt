package com.example.filmes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.databinding.MovieCellBinding
import com.example.filmes.model.Movie
import kotlinx.coroutines.NonDisposableHandle.parent

class MovieAdapter (

    private val lista: List<Movie>,
    private val clickListener: MovieClickListener

    ): RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val from = LayoutInflater.from( parent.context )
        val bindind = MovieCellBinding.inflate( from, parent, false )

        return MovieViewHolder( parent.context, bindind, clickListener )

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie( lista[position] )
    }

    override fun getItemCount(): Int = lista.size

}