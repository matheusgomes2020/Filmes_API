package com.example.filmes.adapter.views


import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.BaseViewHolder
import com.example.filmes.model.general.Cast
import com.example.filmes.ui.person.PersonFragment

class CastView (viewGroup: ViewGroup, private var fragmentManager: FragmentManager) : BaseViewHolder<Cast>(
    R.layout.cast_cell,
    viewGroup
) {

    private val context = viewGroup.context

    override fun bind(item: Cast) {
        itemView.findViewById<TextView>(R.id.textActorName).text = item.name
        if ( !item.profile_path.isNullOrEmpty() ) itemView.findViewById<ImageView>(R.id.imageCast).load( "https://image.tmdb.org/t/p/w500" + item.profile_path)
        else itemView.findViewById<ImageView>(R.id.imageCast).load( R.drawable.padrao)
        itemView.findViewById<LinearLayout>(R.id.containerCast).setOnClickListener {

            /*
            val fragmentManager = fragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(this.itemI,PersonFragment( item.id.toString() ))
            fragmentTransaction.commit()

             */

            PersonFragment(item.id.toString()).show(fragmentManager, "personTag")


        }
    }

}