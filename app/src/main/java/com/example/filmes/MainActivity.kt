package com.example.filmes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmes.adapter.MovieAdapter
import com.example.filmes.adapter.MovieClickListener
import com.example.filmes.databinding.ActivityMainBinding
import com.example.filmes.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var lista: List<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        o()

        setRecyclerView()

    }

    private fun setRecyclerView() {

        val mainActivity = this
        mainViewModel.cc1.observe( this ) {

            binding.movieRecyclerView.apply {
                layoutManager = LinearLayoutManager( applicationContext )
                adapter = MovieAdapter( it )
            }

        }

    }

    private fun o() {

        mainViewModel.cc1.observe(this) {

            lista = it

            Log.d("Lista", "o: $it")

        }

    }

}