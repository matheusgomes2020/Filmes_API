package com.example.filmes.ui.perfil

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.BaseViewHolder
import com.example.filmes.databinding.FragmentProfileBinding
import com.example.filmes.model.MovieFirebase
import com.example.filmes.model.SeriesFirebase
import com.example.filmes.ui.movieDetails.MovieDetailsActivity
import com.example.filmes.ui.seriesDetails.SerieDetailsActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val viewModel: FavoriteViewModel by viewModels()

    private var listOfMovies = emptyList<MovieFirebase>()
    private var listOfSeries = emptyList<SeriesFirebase>()
    private val currentUser = FirebaseAuth.getInstance().currentUser
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
        observeMoviesFirebase()
        observeSeriesFirebase()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeMoviesFirebase() {
        viewModel.getMovies()
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.movieList.collect {
                if (it.isLoading) {
                }
                if (it.error.isNotBlank()) {
                }
                it.data?.let { _movie ->
                    listOfMovies = _movie.filter { movie ->
                        movie.userId == currentUser?.uid.toString() }
                    setRecyclerFavoriteMovies(listOfMovies )
                }
            }
        }
    }

    private fun observeSeriesFirebase() {
        viewModel.getSeries()
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.seriesList.collect {
                if (it.isLoading) {
                }
                if (it.error.isNotBlank()) {
                }
                it.data?.let { _series ->
                    listOfSeries = _series.filter { series ->
                        series.userId == currentUser?.uid.toString() }
                    setRecyclerFavoriteSeries(listOfSeries )
                }
            }
        }
    }

    private fun setRecyclerFavoriteMovies(list: List<MovieFirebase>) {

        binding.recyclerFavoriteMovies.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                FavoriteMovieView(it, viewModel)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerFavoriteSeries(list: List<SeriesFirebase>) {

        binding.recyclerFavoriteSeries.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                FavoriteSeriesView(it, viewModel)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

     class FavoriteMovieView (viewGroup: ViewGroup, private val viewModel: FavoriteViewModel) : BaseViewHolder<MovieFirebase>(
        R.layout.movie_an_series_cell,
        viewGroup
    ) {

        private val context = viewGroup.context
        var movie: MovieFirebase? = null

         private fun showCustomDialogBox(message: String?, movie: MovieFirebase )  {
             val dialog = Dialog(context)
             dialog.requestWindowFeature( Window.FEATURE_NO_TITLE )
             dialog.setCancelable( false )
             dialog.setContentView( R.layout.layout_custom_dialog )
             dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
             val tvMessage: TextView = dialog.findViewById(R.id.tvMessage)
             val btnYes: Button = dialog.findViewById(R.id.btnYes)
             val btnNo: Button = dialog.findViewById(R.id.btnNo)
             tvMessage.text = message
             btnYes.setOnClickListener {
                // viewModel.deleteMovie(movie)
                 dialog.dismiss()
             }
             btnNo.setOnClickListener {
                 dialog.dismiss()
             }
             dialog.show()
         }

        override fun bind(item: MovieFirebase) {
            movie = MovieFirebase(
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
                val message: String? = "Voce tem certeza que deseja remover " + movie!!.title + " de seus favoritos?"
                showCustomDialogBox(message, movie!!)
                //viewModel.deleteMovie(movie!!)
                true
            }
        }
    }

    class FavoriteSeriesView (viewGroup: ViewGroup, private val viewModel: FavoriteViewModel) : BaseViewHolder<SeriesFirebase>(
        R.layout.movie_an_series_cell,
        viewGroup
    ) {

        private val context = viewGroup.context
        var series: SeriesFirebase? = null

        private fun showCustomDialogBox(message: String?, serie: SeriesFirebase)  {
            val dialog = Dialog(context)
            dialog.requestWindowFeature( Window.FEATURE_NO_TITLE )
            dialog.setCancelable( false )
            dialog.setContentView( R.layout.layout_custom_dialog )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val tvMessage: TextView = dialog.findViewById(R.id.tvMessage)
            val btnYes: Button = dialog.findViewById(R.id.btnYes)
            val btnNo: Button = dialog.findViewById(R.id.btnNo)
            tvMessage.text = message
            btnYes.setOnClickListener {
                //viewModel.deleteSeries(serie)
                dialog.dismiss()
            }
            btnNo.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        override fun bind(item: SeriesFirebase) {
            series = SeriesFirebase(
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
                val message: String? = "Voce tem certeza que deseja remover " + series!!.name + " de seus favoritos?"
                showCustomDialogBox(message, series!!)
                true
            }
        }
    }

}


