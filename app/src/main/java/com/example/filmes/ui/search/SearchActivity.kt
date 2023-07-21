package com.example.filmes.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.databinding.ActivitySearchBinding
import com.example.filmes.model.movie.Movie
import com.example.filmes.views.SearchView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observe()

        binding.searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return true
            }
        })
    }

    private fun search(query: String?){

        if (!query.isNullOrEmpty()) {
            viewModel.searchMovies( query )
        } else {
            Toast.makeText(applicationContext, "Digite algo!!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun observe(){

        //viewModel.searchMovies("Black Panther")
        viewModel.popularMovies.observe( this ) {
            setRecyclerViewSearch( it )
        }
    }

    private fun setRecyclerViewSearch(list: List<Movie>) {

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                SearchView(it)
            }.apply {
                items = list.toMutableList()
            }
        }
    }
}