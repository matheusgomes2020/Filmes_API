package com.example.filmes.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.R
import com.example.filmes.adapter.MovieAdapter
import com.example.filmes.adapter.MovieClickListener
import com.example.filmes.databinding.ActivityMainBinding
import com.example.filmes.model.Movie
import com.example.filmes.ui.movieDetails.MovieDetailsActivity
import com.example.filmes.ui.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MovieClickListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToobar()
        setRecyclerView()

    }

    private fun setupToobar(){
        setSupportActionBar(binding.myToolbar)
        binding.myToolbar.setTitleTextColor(resources.getColor(R.color.white))
    }

    private fun setRecyclerView() {

        val mainActivity = this
        mainViewModel.popularMovies.observe( this ) {

            binding.movieRecyclerViewEmAlta.apply {
                layoutManager = LinearLayoutManager( applicationContext, RecyclerView.HORIZONTAL, false)
                adapter = MovieAdapter( it, mainActivity )
            }
        }

        mainViewModel.nowPlayingovies.observe( this ) {

            binding.movieRecyclerViewEmCartaz.apply {
                layoutManager = LinearLayoutManager( applicationContext, RecyclerView.HORIZONTAL, false)
                adapter = MovieAdapter( it, mainActivity )
            }
        }

        mainViewModel.upcomingMovies.observe( this ) {

            binding.movieRecyclerViewLancamentos.apply {
                layoutManager = LinearLayoutManager( applicationContext, RecyclerView.HORIZONTAL, false)
                adapter = MovieAdapter( it, mainActivity )
            }
        }

        mainViewModel.ratedMovies.observe( this ) {

            binding.movieRecyclerViewMelhores.apply {
                layoutManager = LinearLayoutManager( applicationContext, RecyclerView.HORIZONTAL, false)
                adapter = MovieAdapter( it, mainActivity )
            }
        }

    }

    override fun clickMovie(movie: Movie) {

        val id = movie.id.toString()
        val intent = Intent(this, MovieDetailsActivity::class.java).apply {
            putExtra("id", id)

        }
        startActivity( intent )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.actionbar_munu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.search -> {

                val intent = Intent(this, SearchActivity::class.java)
                startActivity( intent )
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}