package com.example.filmes.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.adapter.movie.MovieClickListener
import com.example.filmes.adapter.movie.MovieSearchAdapter
import com.example.filmes.databinding.ActivitySearchBinding
import com.example.filmes.model.Movie
import com.example.filmes.ui.movieDetails.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), MovieClickListener {

    private lateinit var binding: ActivitySearchBinding

    private val viewModel: SearchViewModel by viewModels()

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

        viewModel.searchMovies("Black Panther")

        viewModel.popularMovies.observe( this ) {



            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager( applicationContext, RecyclerView.VERTICAL, false)
                adapter = MovieSearchAdapter( it, searchActivity )
            }

        }



    }

    override fun clickMovie(movie: Movie) {

        val mensagem = movie.title
        val id = movie.id.toString()
//            val m = lista[0]
        Toast.makeText(applicationContext, "Deu certo!!!", Toast.LENGTH_LONG).show()
        Toast.makeText(applicationContext, "ID: " + id, Toast.LENGTH_LONG).show()
        val intent = Intent(this, MovieDetailsActivity::class.java).apply {
            putExtra("mensagem", mensagem)
            putExtra("id", id)
            //putExtra("obj", m.overview)


        }

        startActivity( intent )

    }

}