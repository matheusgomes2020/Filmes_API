package com.example.filmes.views


import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.BaseViewHolder
import com.example.filmes.model.CastX

class CastView (viewGroup: ViewGroup) : BaseViewHolder<CastX>(
    R.layout.cast_cell,
    viewGroup
) {

    private val context = viewGroup.context

    override fun bind(item: CastX) {
        itemView.findViewById<TextView>(R.id.textActorName).text = item.name
        if ( !item.profile_path.isNullOrEmpty() ) itemView.findViewById<ImageView>(R.id.imageCast).load( "https://image.tmdb.org/t/p/w500" + item.profile_path)
        else itemView.findViewById<ImageView>(R.id.imageCast).load( R.drawable.padrao)
        itemView.findViewById<LinearLayout>(R.id.containerCast).setOnClickListener {
            Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
        }
    }
}