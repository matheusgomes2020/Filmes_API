package com.example.filmes.ui.movieDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.adapter.MovieAdapter
import com.example.filmes.databinding.ActivityMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Exception


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    private val viewModel: MovieDetailsViewModel by viewModels()
   // private lateinit var idd: String


    var ov = "OVEr"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView( binding.root )

        val mensagem = intent.getStringExtra("mensagem")
        val id = intent.getStringExtra("id")
        val o = intent.getStringExtra("obj")

       // idd = id!!

        binding.idPassado.text = "ID: " + id

        bb()

        viewModel.getMovieInfo(id!!)
        //Toast.makeText(applicationContext, binding.movieOverview.text.toString(), Toast.LENGTH_LONG).show()


       // aa()



    }





    fun bb() {

        try {
            viewModel.movieInfo.observe(this) {

                binding.movieOverview.text = it.overview
                binding.movieTitle.text = it.title
                binding.movieID.text = it.id.toString()
                binding.imageView2.load("https://image.tmdb.org/t/p/w500" + it.poster_path)

            }
            }catch (e: Exception){
            e.printStackTrace()
        }



        }




}










