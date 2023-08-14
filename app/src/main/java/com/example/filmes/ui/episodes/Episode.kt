package com.example.filmes.ui.episodes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.databinding.FragmentEpisodeBinding
import com.example.filmes.model.general.Cast
import com.example.filmes.model.general.Image
import com.example.filmes.model.general.Images
import com.example.filmes.model.person.Profile
import com.example.filmes.adapter.views.CastView
import com.example.filmes.adapter.views.EpidoseImagesView
import com.example.filmes.adapter.views.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.lang.Exception

@AndroidEntryPoint
class Episode(private var seriesId: String, private var seasonNumber: Int, private var episodeNumber: Int ): BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEpisodeBinding
    private val  episodeViewModel: EpisodeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        episodeViewModel.getEpisodeInfo( seriesId, seasonNumber, episodeNumber )
        observe()
    }

    private fun observe(){

        lifecycle.coroutineScope.launchWhenCreated {
            episodeViewModel.episodeData.collect {
                if (it.isLoading) { }
                if (it.error.isNotBlank()) { }
                it.data?.let { _episode ->
                    var roteiro = ""
                    if (!_episode.name.isNullOrEmpty()) binding.textView10.text = _episode.name else binding.textView10.visibility = View.GONE
                    if (!_episode.overview.isNullOrEmpty()) binding.textView11.text = _episode.overview else binding.textView11.visibility = View.GONE
                    var director = ""
                    var writing = ""
                    for (i in _episode.crew) if ( i.job == "Director" ) {binding.textViewDirecaoEdisodio.text = i.name
                        director = i.name }
                    for (i in _episode.crew) if ( i.department == "Writing" ) { roteiro += i.name + "\n"
                        writing = i.name }
                    binding.textViewRoteiroEdisodio.text = roteiro
                    if (director.isNullOrEmpty()) {binding.textViewDirecaoEdisodio.visibility = View.GONE
                        binding.textView8.visibility = View.GONE}
                    if (writing.isNullOrEmpty()) {binding.textViewRoteiroEdisodio.visibility = View.GONE
                        binding.textView9.visibility = View.GONE}
                    if ( !_episode.guest_stars.isNullOrEmpty() ) setRecyclerViewCast(_episode.guest_stars)
                    else binding.linearLayout2.visibility = View.GONE
                    if ( _episode.images.stills.isNullOrEmpty() ) binding.recyclerEpisodeImages.visibility = View.GONE
                    else setRecyclerViewImages( _episode.images.stills!! )
                }
            }
        }

    }
    private fun setRecyclerViewCast(list: List<Cast>) {

        binding.recyclerMoviecast.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                CastView(it, childFragmentManager )
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerViewImages(list: List<Profile>) {

        binding.recyclerEpisodeImages.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                EpidoseImagesView(it, childFragmentManager)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEpisodeBinding.inflate(inflater,container,false)
        return binding.root
    }
}