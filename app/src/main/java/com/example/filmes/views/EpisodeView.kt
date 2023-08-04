package com.example.filmes.views

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.BaseViewHolder
import com.example.filmes.ui.episodes.Episode

class EpisodeView (viewGroup: ViewGroup, private var seriesId: String, private var fragmentManager: FragmentManager ) : BaseViewHolder<com.example.filmes.model.serie.Episode>(
    R.layout.episode_cell,
    viewGroup
) {

    private val context = viewGroup.context

    override fun bind(item: com.example.filmes.model.serie.Episode) {
        itemView.findViewById<TextView>(R.id.textNameEpisode).text = item.episode_number.toString() + " - " + item.name
        if ( !item.overview.isNullOrEmpty() ) itemView.findViewById<TextView>(R.id.textOverviewEpisode).text = item.overview
        else itemView.findViewById<TextView>(R.id.textOverviewEpisode).text = "Sem overview!!!"
        if ( !item.still_path.isNullOrEmpty() ) itemView.findViewById<ImageView>(R.id.imageEpisode).load( "https://image.tmdb.org/t/p/w500" + item.still_path)
        else itemView.findViewById<ImageView>(R.id.imageEpisode).load(R.drawable.logo)
        itemView.findViewById<LinearLayout>(R.id.episode_cellContainer).setOnClickListener {

            Episode(seriesId,item.season_number, item.episode_number).show(fragmentManager , "seasonTag")

        }
    }
}