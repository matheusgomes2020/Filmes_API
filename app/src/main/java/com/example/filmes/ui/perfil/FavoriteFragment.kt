package com.example.filmes.ui.perfil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.BaseViewHolder
import com.example.filmes.databinding.FragmentProfileBinding
import com.example.filmes.model.MovieRoom
import com.example.filmes.model.SerieRoom
import com.example.filmes.ui.movieDetails.MovieDetailsActivity
import com.example.filmes.ui.seriesDetails.SerieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val profileViewModel: FavoriteViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        observeSeries()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun observe() {

        try {
            profileViewModel.movieList.observe(viewLifecycleOwner) {
                setRecyclerViewPersonMovies( it ) }
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun observeSeries() {

        try {
            profileViewModel.seriesList.observe(viewLifecycleOwner) {
                setRecyclerViewPersonSeries( it ) }
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setRecyclerViewPersonMovies(list: List<MovieRoom>) {

        binding.recyclerPerfil.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                MovieRoomView(it, profileViewModel)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerViewPersonSeries(list: List<SerieRoom>) {

        binding.recyclerSeries.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                SeriesRoomView(it, profileViewModel)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

     class MovieRoomView (viewGroup: ViewGroup, private val viewModel: FavoriteViewModel) : BaseViewHolder<MovieRoom>(
        R.layout.movie_an_series_cell,
        viewGroup
    ) {

        private val context = viewGroup.context
        var movie: MovieRoom? = null

        override fun bind(item: MovieRoom) {
            movie = MovieRoom(
                item.id,
                item.poster_path ,
                item.title
            )
            itemView.findViewById<TextView>(R.id.nomeOrTitle).text = item.title
            if ( item.poster_path.isNullOrEmpty() ) itemView.findViewById<ImageView>(R.id.imageViewMovieAndSeries).load(
                R.drawable.padrao)
            else itemView.findViewById<ImageView>(R.id.imageViewMovieAndSeries).load("https://image.tmdb.org/t/p/w500" + item.poster_path)
            itemView.findViewById<ConstraintLayout>(R.id.movieAndSeriesCellContainer).setOnClickListener {
                val intent = Intent( context, MovieDetailsActivity::class.java ).apply {
                    putExtra("id", item.id.toString() )
                }
                context.startActivity(intent)
            }
            itemView.findViewById<ConstraintLayout>(R.id.movieAndSeriesCellContainer).setOnLongClickListener {
                viewModel.deleteMovie(movie!!)
                true
            }
        }
    }

    class SeriesRoomView (viewGroup: ViewGroup, private val viewModel: FavoriteViewModel) : BaseViewHolder<SerieRoom>(
        R.layout.movie_an_series_cell,
        viewGroup
    ) {

        private val context = viewGroup.context
        var series: SerieRoom? = null

        override fun bind(item: SerieRoom) {
            series = SerieRoom(
                item.id,
                item.poster_path ,
                item.name
            )
            itemView.findViewById<TextView>(R.id.nomeOrTitle).text = item.name
            if ( item.poster_path.isNullOrEmpty() ) itemView.findViewById<ImageView>(R.id.imageViewMovieAndSeries).load(
                R.drawable.padrao)
            else itemView.findViewById<ImageView>(R.id.imageViewMovieAndSeries).load("https://image.tmdb.org/t/p/w500" + item.poster_path)
            itemView.findViewById<ConstraintLayout>(R.id.movieAndSeriesCellContainer).setOnClickListener {
                val intent = Intent( context, SerieDetailsActivity::class.java ).apply {
                    putExtra("id", item.id.toString() )
                }
                context.startActivity(intent)
            }
            itemView.findViewById<ConstraintLayout>(R.id.movieAndSeriesCellContainer).setOnLongClickListener {
                viewModel.deleteSeries(series!!)
                true
            }
        }
    }

}


