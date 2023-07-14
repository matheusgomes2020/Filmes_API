package com.example.filmes.ui.episodes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.filmes.databinding.FragmentEpisodeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class Episode( var serieId: String, var seasonNumber: Int, var episodeNumber: Int ): BottomSheetDialogFragment() {

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

            }

        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEpisodeBinding.inflate(inflater,container,false)
        return binding.root
    }

}