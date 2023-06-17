package com.example.filmes.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.databinding.ActivityMainBinding
import com.example.filmes.databinding.MovieCellBinding
import com.example.filmes.model.Movie

class MovieViewHolder (

    private val context: Context,
    private val binding: MovieCellBinding,
    //private val clickListener: MovieClickListener

    ): RecyclerView.ViewHolder( binding.root ) {

        fun bindMovie( movie: Movie ) {

            binding.imageView.load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
            binding.nome.text = movie.id.toString()
            binding.titulo.text = movie.overview

        }

    }