package com.example.filmes.ui.personImage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.load
import com.example.filmes.R
import com.example.filmes.databinding.FragmentPersonBinding
import com.example.filmes.databinding.FragmentPersonImageBinding
import com.example.filmes.ui.episodes.EpisodeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PersonImageFragment(private var imageLink: String) : BottomSheetDialogFragment() {

    private var _binding: FragmentPersonImageBinding? = null
    private val binding get() = _binding!!
    private val  personImageViewModel: PersonImageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPersonImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        personImageViewModel.loadImage( imageLink )
        observe()

    }

    private fun observe(){

        personImageViewModel.personInfo.observe(viewLifecycleOwner){

            binding.imageView9.load( "https://image.tmdb.org/t/p/w500" + it )

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}