package com.example.filmes.adapter.serie

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.databinding.MovieAnSeriesCellBinding
import com.example.filmes.model.Serie

class SerieViewHolder (

    private val context: Context,
    private val binding: MovieAnSeriesCellBinding,
    private val clickListener: SerieClickListener

    ): RecyclerView.ViewHolder( binding.root ) {

        fun bindMovie( serie: Serie) {

            binding.imageViewMovieAndSeries.load("https://image.tmdb.org/t/p/w500" + serie.poster_path)
            binding.nomeOrTitle.text = serie.name
            //binding.titulo.text = serie.overview

            binding.movieAndSeriesCellContainer.setOnClickListener {

                clickListener.clickSerie(serie)

            }

        }

    }