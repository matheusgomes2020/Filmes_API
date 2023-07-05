package com.example.filmes.ui.searchSeries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.adapter.movie.MovieClickListener
import com.example.filmes.adapter.movie.MovieSearchAdapter
import com.example.filmes.adapter.serie.SerieClickListener
import com.example.filmes.adapter.serie.SerieSearchAdapter
import com.example.filmes.databinding.ActivitySearchBinding
import com.example.filmes.model.Movie
import com.example.filmes.model.Serie
import com.example.filmes.ui.movieDetails.MovieDetailsActivity
import com.example.filmes.ui.seriesDetails.SerieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SerieSearchActivity : AppCompatActivity(), SerieClickListener {

    private lateinit var binding: ActivitySearchBinding

    private val viewModel: SerieSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()

        binding.searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                bb(newText)
                return true
            }

        })


    }

    private fun bb(query: String?){


        if (!query.isNullOrEmpty()) {
            viewModel.searchMovies( query )
        } else {
            Toast.makeText(applicationContext, "Digite algo!!!", Toast.LENGTH_LONG).show()
        }

    }

    private fun setRecyclerView() {

        val searchActivity = this

        viewModel.searchMovies("Agents of SHIELD")

        viewModel.series.observe( this ) {



            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager( applicationContext, RecyclerView.VERTICAL, false)
                adapter = SerieSearchAdapter( it, searchActivity )
            }

        }



    }

    override fun clickMovie( serie: Serie ) {

        val mensagem = serie.name
        val id = serie.id.toString()
//            val m = lista[0]
        Toast.makeText(applicationContext, "Deu certo!!!", Toast.LENGTH_LONG).show()
        Toast.makeText(applicationContext, "ID: " + id, Toast.LENGTH_LONG).show()
        val intent = Intent(this, SerieDetailsActivity::class.java).apply {
            putExtra("mensagem", mensagem)
            putExtra("id", id)
            //putExtra("obj", m.overview)


        }

        startActivity( intent )

    }

}