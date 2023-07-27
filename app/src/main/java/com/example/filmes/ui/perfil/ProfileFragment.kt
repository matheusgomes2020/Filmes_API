package com.example.filmes.ui.perfil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.runtime.collectAsState
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.BaseViewHolder
import com.example.filmes.databinding.FragmentProfileBinding
import com.example.filmes.model.MovieD
import com.example.filmes.model.movie.Movie
import com.example.filmes.model.person.Cast
import com.example.filmes.model.person.MovieCredits
import com.example.filmes.ui.movieDetails.MovieDetailsActivity
import com.example.filmes.ui.person.PersonFragment
import com.example.filmes.views.MovieView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val profileViewModel: ProfileViewModel by viewModels()
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

    private fun setRecyclerViewPersonMovies(list: List<MovieD>) {

        binding.recyclerPerfil.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                MovieDView(it, profileViewModel)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

     class MovieDView (viewGroup: ViewGroup, private val viewModel: ProfileViewModel) : BaseViewHolder<MovieD>(
        R.layout.movie_an_series_cell,
        viewGroup
    ) {

        private val context = viewGroup.context
        var movie: MovieD? = null

        override fun bind(item: MovieD) {
            movie = MovieD(
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

}


