package com.example.filmes.ui.person

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.BaseViewHolder
import com.example.filmes.databinding.FragmentPersonBinding
import com.example.filmes.model.person.Cast
import com.example.filmes.model.person.CastX
import com.example.filmes.model.person.Profile
import com.example.filmes.ui.movieDetails.MovieDetailsActivity
import com.example.filmes.ui.seriesDetails.SerieDetailsActivity
import com.example.filmes.adapter.views.ImageView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class PersonFragment( private var personId: String) : BottomSheetDialogFragment() {

    private val viewModel: PersonViewModel by viewModels()
    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPersonInfo(personId)
        observe()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {

        try {
            viewModel.personInfo.observe( viewLifecycleOwner ) {
                binding.textPersonName.text = it.name
                binding.textPersonBiography.text = it.biography
                binding.textPersonData.text = it.birthday
                binding.textPersonLocal.text = it.place_of_birth
                if ( it.images.profiles.isNullOrEmpty() ) binding.recyclePersonImages.visibility = View.GONE
                else setRecyclerViewImages( it.images.profiles!! )
                setRecyclerViewSeries( it.tv_credits.cast )
                setRecyclerViewMovies( it.movie_credits.cast )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setRecyclerViewImages(list: List<Profile>) {

        binding.recyclePersonImages.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                ImageView(it, childFragmentManager)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerViewSeries(list: List<CastX>) {

        binding.recyclerPersonSeries.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                PersonSeriesView(it)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerViewMovies(list: List<Cast>) {

        binding.recyclerPersonMovies.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                PersonMoviesView(it)
            }.apply {
                items = list.toMutableList()
            }
        }
    }
    class PersonSeriesView(viewGroup: ViewGroup) : BaseViewHolder<CastX>(
        R.layout.movie_an_series_cell,
        viewGroup
    ) {
        private val context = viewGroup.context
        override fun bind(item: CastX) {
            itemView.findViewById<TextView>(R.id.nomeOrTitle).text = item.name
            if ( !item.poster_path.isNullOrEmpty() ) itemView.findViewById<android.widget.ImageView>(
                R.id.imageViewMovieAndSeries).load( "https://image.tmdb.org/t/p/w500" + item.poster_path)
            else itemView.findViewById<android.widget.ImageView>(R.id.imageViewMovieAndSeries).load( R.drawable.padrao)
            itemView.findViewById<ShimmerFrameLayout>(R.id.shimmerMoviesAndSeriesCell).visibility = View.GONE
            itemView.findViewById<TextView>(R.id.nomeOrTitle).visibility = View.VISIBLE
            itemView.findViewById<android.widget.ImageView>(R.id.imageViewMovieAndSeries).visibility = View.VISIBLE
            itemView.findViewById<LinearLayout>(R.id.movieAndSeriesCellContainer).setOnClickListener {
                val intent = Intent( context, SerieDetailsActivity::class.java ).apply {
                    putExtra("id", item.id.toString() )
                }
                context.startActivity(intent)
            }
        }
    }

    class PersonMoviesView(viewGroup: ViewGroup) : BaseViewHolder<Cast>(
        R.layout.movie_an_series_cell,
        viewGroup
    ) {
        private val context = viewGroup.context
        override fun bind(item: Cast) {
            itemView.findViewById<TextView>(R.id.nomeOrTitle).text = item.title
            if ( !item.poster_path.isNullOrEmpty() ) itemView.findViewById<android.widget.ImageView>(
                R.id.imageViewMovieAndSeries).load( "https://image.tmdb.org/t/p/w500" + item.poster_path)
            else itemView.findViewById<android.widget.ImageView>(R.id.imageViewMovieAndSeries).load( R.drawable.padrao)
            itemView.findViewById<ShimmerFrameLayout>(R.id.shimmerMoviesAndSeriesCell).visibility = View.GONE
            itemView.findViewById<TextView>(R.id.nomeOrTitle).visibility = View.VISIBLE
            itemView.findViewById<android.widget.ImageView>(R.id.imageViewMovieAndSeries).visibility = View.VISIBLE
            itemView.findViewById<LinearLayout>(R.id.movieAndSeriesCellContainer).setOnClickListener {
                val intent = Intent( context, MovieDetailsActivity::class.java ).apply {
                    putExtra("id", item.id.toString() )
                }
                context.startActivity(intent)
            }
        }
    }
}