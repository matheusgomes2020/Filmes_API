package com.example.filmes.ui.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.databinding.FragmentPersonBinding
import com.example.filmes.model.person.Profile
import com.example.filmes.views.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class PersonFragment( private var personId: String) : BottomSheetDialogFragment() {

    private val viewModel: PersonViewModel by viewModels()
    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPersonInfo(personId)
        observe()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {

        try {
            viewModel.personInfo.observe( viewLifecycleOwner ) {
                binding.textPersonName.text = it.name
                binding.textPersonBiography.text = it.biography
                binding.textPersonData.text = it.birthday
                binding.textPersonLocal.text = it.place_of_birth

                if ( it.images.profiles.isNullOrEmpty() ) binding.recyclePersonImages.visibility = View.GONE
                else setRecyclerViewImages( it.images.profiles!! )

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun setRecyclerViewImages(list: List<Profile>) {

        binding.recyclePersonImages.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                ImageView(it, childFragmentManager)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

}