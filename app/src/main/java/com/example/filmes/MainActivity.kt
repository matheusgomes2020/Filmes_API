package com.example.filmes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.adapter.MovieAdapter
import com.example.filmes.databinding.ActivityMainBinding
import com.example.filmes.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var lista: List<Movie>
    private lateinit var lista2: List<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        o()

        setRecyclerView()

    }

    private fun setRecyclerView() {

        val mainActivity = this
        mainViewModel.popularMovies.observe( this ) {

            binding.movieRecyclerView.apply {
                layoutManager = LinearLayoutManager( applicationContext, RecyclerView.HORIZONTAL, false)
                adapter = MovieAdapter( it )
            }

        }

        mainViewModel.ratedMovies.observe( this ) {

            binding.movieRecyclerView2.apply {
                layoutManager = LinearLayoutManager( applicationContext, RecyclerView.HORIZONTAL, false)
                adapter = MovieAdapter( it )
            }

        }

    }

    private fun o() {

        mainViewModel.popularMovies.observe(this) {

            lista = it

            Log.d("Lista", "o: $it")

        }

    }

}