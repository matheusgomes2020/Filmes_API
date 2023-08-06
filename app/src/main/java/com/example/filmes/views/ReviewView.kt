package com.example.filmes.views

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.BaseViewHolder
import com.example.filmes.model.general.Review
import com.example.filmes.model.person.Profile

class ReviewView (viewGroup: ViewGroup, private var fragmentManager: FragmentManager) : BaseViewHolder<Review>(
    R.layout.review_cell,
    viewGroup
) {

    private val context = viewGroup.context

    override fun bind(item: Review) {
        itemView.findViewById<TextView>(R.id.textNameReview).text = item.author
        itemView.findViewById<TextView>(R.id.textReviewDate).text = item.created_at
        var rating = ""
        when (item.author_details.rating.toDouble()) {
            in 0.0..1.9 -> rating = "⭐"
            in 2.0..3.9 -> rating = "⭐⭐"
            in 4.0..5.9 -> rating = "⭐⭐⭐"
            in 6.0..7.9 -> rating = "⭐⭐⭐⭐"
            in 8.0..10.0 -> rating = "⭐⭐⭐⭐⭐"
        }
        itemView.findViewById<TextView>(R.id.ratingReviewCell).text = rating
        itemView.findViewById<TextView>(R.id.textReview).text = item.content
        if ( !item.author.isNullOrEmpty() ) itemView.findViewById<ImageView>(R.id.imageReview).load( "https://image.tmdb.org/t/p/w500" + item.author_details.avatar_path)
        else itemView.findViewById<ImageView>(R.id.imageReview).load(R.drawable.padrao)
        itemView.findViewById<LinearLayout>(R.id.review_cellContainer).setOnClickListener {



        }
    }

}