package com.example.filmes.ui.season

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.adapter.episodes.EpisodeClickListener
import com.example.filmes.adapter.episodes.EpisodesAdapter
import com.example.filmes.adapter.movie.MovieAdapter
import com.example.filmes.databinding.FragmentSeasonBinding
import com.example.filmes.model.Episode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeasonFragment : Fragment(), EpisodeClickListener {

    private var _binding: FragmentSeasonBinding? = null
    private val binding get() = _binding!!
    private val seasonViewModel: SeasonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSeasonBinding.inflate(inflater, container, false)

        //val id = Intentintent.getStringExtra("id")

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setRecyclerView(){

        val mainActivity = this
        seasonViewModel.season.observe(viewLifecycleOwner) {

            binding.recyclerEpisodes.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                adapter = EpisodesAdapter(it.episodes, mainActivity)
            }
        }

    }

    override fun clickEpisode(episode: com.example.filmes.di.Episode) {
        Toast.makeText( context, episode.name, Toast.LENGTH_LONG).show()

    }


}