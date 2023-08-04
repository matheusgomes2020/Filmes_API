package com.example.filmes.views

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.BaseViewHolder
import com.example.filmes.model.person.Profile

class ImageView (viewGroup: ViewGroup, private var fragmentManager: FragmentManager) : BaseViewHolder<Profile>(
    R.layout.image_cell,
    viewGroup
) {

    private val context = viewGroup.context

    override fun bind(item: Profile) {
        if ( !item.file_path.isNullOrEmpty() ) itemView.findViewById<ImageView>(R.id.imageP).load( "https://image.tmdb.org/t/p/w500" + item.file_path)
        else itemView.findViewById<ImageView>(R.id.imageP).load(R.drawable.logo)
        itemView.findViewById<LinearLayout>(R.id.containerImage).setOnClickListener {



        }
    }

}