package com.example.filmes.views

import android.app.Activity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.BaseViewHolder
import com.example.filmes.di.Episode2
import com.example.filmes.ui.episodes.Episode

class EpisodeView (viewGroup: ViewGroup, var serieId: String, ) : BaseViewHolder<com.example.filmes.di.Episode>(
    R.layout.episode_cell,
    viewGroup
) {

    private val context = viewGroup.context

    override fun bind(item: com.example.filmes.di.Episode) {
        itemView.findViewById<TextView>(R.id.textNameEpisode).text = item.name
        if ( !item.still_path.isNullOrEmpty() ) itemView.findViewById<TextView>(R.id.textOverviewEpisode).text = item.overview
        else itemView.findViewById<TextView>(R.id.textOverviewEpisode).text = ""
        if ( !item.still_path.isNullOrEmpty() ) itemView.findViewById<ImageView>(R.id.imageEpisode).load( "https://image.tmdb.org/t/p/w500" + item.still_path)
        else itemView.findViewById<ImageView>(R.id.imageEpisode).load( R.drawable.padrao)
        itemView.findViewById<LinearLayout>(R.id.episode_cellContainer).setOnClickListener {

            var fragment = Episode(serieId,item.season_number, item.episode_number)
            var framManager = (itemView.context as Activity).fragmentManager
            val ft: FragmentManager = fragment.childFragmentManager
            if (ft != null) {
                ft.commit()
            }
        }
    }
}

private fun FragmentManager.commit() {
    TODO("Not yet implemented")
}
