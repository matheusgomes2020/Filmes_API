package com.example.filmes.adapter.movie

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.databinding.SearchMovieCellBinding
import com.example.filmes.model.Movie

class MovieSearchViewHolder (

    private val context: Context,
    private val binding: SearchMovieCellBinding,
    private val clickListener: MovieClickListener

): RecyclerView.ViewHolder( binding.root ) {

    fun bindMovie( movie: Movie) {

        binding.imageSearchCell.load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
        binding.nomeSearchCell.text = movie.title
        //binding.textGenreSearchCell.text = movie.genres[0].toString()



        when ( movie.vote_average ) {

           in 0.0..1.9 -> binding.ratingSearchCell.text = "🌟⭐⭐⭐⭐"
           in 2.0..3.9 -> binding.ratingSearchCell.text = "🌟🌟⭐⭐⭐"
           in 4.0..5.9 -> binding.ratingSearchCell.text = "🌟🌟🌟⭐⭐"
           in 6.0..7.9 -> binding.ratingSearchCell.text = "🌟🌟🌟🌟⭐"
           in 8.0..10.0 -> binding.ratingSearchCell.text = "🌟🌟🌟🌟🌟"

        }

        binding.movieSearchCellContainer.setOnClickListener {

            clickListener.clickMovie(movie)

        }

    }

}