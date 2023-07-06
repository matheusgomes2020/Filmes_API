package com.example.filmes.adapter.serie

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.databinding.MovieCellBinding
import com.example.filmes.model.Serie

class SerieViewHolder (

    private val context: Context,
    private val binding: MovieCellBinding,
    private val clickListener: SerieClickListener

    ): RecyclerView.ViewHolder( binding.root ) {

        fun bindMovie( serie: Serie) {

            binding.imageView.load("https://image.tmdb.org/t/p/w500" + serie.poster_path)
            binding.nome.text = serie.name
            //binding.titulo.text = serie.overview

            binding.movieCellContainer.setOnClickListener {

                clickListener.clickMovie(serie)

            }

        }

    }