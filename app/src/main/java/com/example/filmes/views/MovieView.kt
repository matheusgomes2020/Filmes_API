package com.example.filmes.views

import android.content.Intent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.BaseViewHolder
import com.example.filmes.model.Movie
import com.example.filmes.ui.movieDetails.MovieDetailsActivity

class MovieView (viewGroup: ViewGroup) : BaseViewHolder<Movie>(
    R.layout.movie_an_series_cell,
    viewGroup
) {

    private val context = viewGroup.context

    override fun bind(item: Movie) {
        itemView.findViewById<TextView>(R.id.nomeOrTitle).text = item.title
        if ( item.poster_path.isNullOrEmpty() ) itemView.findViewById<ImageView>(R.id.imageViewMovieAndSeries).load(R.drawable.padrao)
        else itemView.findViewById<ImageView>(R.id.imageViewMovieAndSeries).load("https://image.tmdb.org/t/p/w500" + item.poster_path)
        itemView.findViewById<ConstraintLayout>(R.id.movieAndSeriesCellContainer).setOnClickListener {
            val intent = Intent( context, MovieDetailsActivity::class.java ).apply {
                putExtra("id", item.id.toString() )
            }
            context.startActivity(intent)
        }
    }
}