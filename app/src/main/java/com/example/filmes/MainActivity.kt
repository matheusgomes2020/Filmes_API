package com.example.filmes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.adapter.MovieAdapter
import com.example.filmes.databinding.ActivityMainBinding
import com.example.filmes.model.Movie
import com.example.filmes.ui.movieDetails.MovieDetailsActivity
import com.example.filmes.ui.search.SearchViewModel
import com.example.filmes.ui.search.SearchViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val mainViewModel: MainViewModel by viewModels()
    private val sViewModel: SearchViewModel by viewModels()
    private lateinit var lista: List<Movie>
    private lateinit var lista2: List<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setRecyclerView()



        binding.newTaskButton.setOnClickListener {

            val mensagem = "Olá"
            val id = "569094"
//            val m = lista[0]
            Toast.makeText(applicationContext, "Test", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MovieDetailsActivity::class.java).apply {
                putExtra("mensagem", mensagem)
                putExtra("id", id)
                //putExtra("obj", m.overview)


            }

            startActivity( intent )
        }

    }



    private fun setRecyclerView() {

        val mainActivity = this
        mainViewModel.popularMovies.observe( this ) {

            binding.movieRecyclerView.apply {
                layoutManager = LinearLayoutManager( applicationContext, RecyclerView.HORIZONTAL, false)
                adapter = MovieAdapter( it )
            }

        }

        sViewModel.searchMovies("Black Panther")

        sViewModel.popularMovies.observe( this ) {

            sViewModel.state.observe(this, Observer {
                it?.let {
                    binding.textView2.text = it
                }
            })

            binding.movieRecyclerView2.apply {
                layoutManager = LinearLayoutManager( applicationContext, RecyclerView.HORIZONTAL, false)
                adapter = MovieAdapter( it )
            }

        }

    }


}