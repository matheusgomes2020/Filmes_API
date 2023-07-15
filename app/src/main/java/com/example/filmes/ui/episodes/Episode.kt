package com.example.filmes.ui.episodes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.adapter.cast.CastAdapter
import com.example.filmes.adapter.cast.CastClickListener
import com.example.filmes.adapter.guest.GuestAdapter
import com.example.filmes.adapter.guest.GuestClickListener
import com.example.filmes.databinding.FragmentEpisodeBinding
import com.example.filmes.di.GuestStarX
import com.example.filmes.model.CastX
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class Episode( var serieId: String, var seasonNumber: Int, var episodeNumber: Int ): BottomSheetDialogFragment(), GuestClickListener {

    private lateinit var binding: FragmentEpisodeBinding
    private val  episodeViewModel: EpisodeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        episodeViewModel.getEpisodeInfo( serieId, seasonNumber, episodeNumber )
        Log.d( "UTG", "Episode: " + "Id: " + serieId + "\nSeason: " + seasonNumber + "\nEpisode: " + episodeNumber)

        observe()

    }

    fun observe(){

        try {

            episodeViewModel.episodeInfo.observe(viewLifecycleOwner) {

                Log.d("OPTAG", "observe: " + it)

                binding.textView10.text = it.name
                binding.textView11.text = it.overview

                setRecyclerViewCast(it.guest_stars)

            }

        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    private fun setRecyclerViewCast(lista: List<GuestStarX>) {

        val mainActivity = this
        binding.recyclerMoviecast.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = GuestAdapter( lista, mainActivity )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEpisodeBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun clickCast(cast: GuestStarX) {
        Toast.makeText(context, cast.name, Toast.LENGTH_SHORT).show()
    }

}