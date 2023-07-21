package com.example.filmes.ui.searchSeries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.databinding.ActivitySearchBinding
import com.example.filmes.model.serie.Serie
import com.example.filmes.views.SearchSeriesView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SerieSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private val viewModel: SerieSearchViewModel by viewModels()

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
            viewModel.searchSeries( query )
        } else {
            Toast.makeText(applicationContext, "Digite algo!!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun observe(){

        //viewModel.searchSeries("Game of Thrones")
        viewModel.series.observe( this ) {
            setRecyclerViewSearch( it )
        }
    }
    private fun setRecyclerViewSearch(list: List<Serie>) {

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                SearchSeriesView(it)
            }.apply {
                items = list.toMutableList()
            }
        }
    }
}