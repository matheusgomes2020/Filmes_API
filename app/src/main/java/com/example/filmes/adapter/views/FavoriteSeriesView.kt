package com.example.filmes.adapter.views

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.BaseViewHolder
import com.example.filmes.model.SeriesFirebase
import com.example.filmes.ui.perfil.FavoriteFragment
import com.example.filmes.ui.perfil.FavoriteViewModel
import com.example.filmes.ui.seriesDetails.SerieDetailsActivity

class FavoriteSeriesView (viewGroup: ViewGroup, private val viewModel: FavoriteViewModel, private val fm: FavoriteFragment) : BaseViewHolder<SeriesFirebase>(
    R.layout.movie_an_series_cell,
    viewGroup
) {

    private val context = viewGroup.context
    var series: SeriesFirebase? = null

    private fun showCustomDialogBox(message: String?, serie: SeriesFirebase)  {
        Log.d("TESSSSTE", "showCustomDialogBox: " + serie.idFirebase)
        val dialog = Dialog(context)
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE )
        dialog.setCancelable( false )
        dialog.setContentView( R.layout.layout_custom_dialog )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val tvMessage: TextView = dialog.findViewById(R.id.tvMessage)
        val btnYes: Button = dialog.findViewById(R.id.btnYes)
        val btnNo: Button = dialog.findViewById(R.id.btnNo)
        tvMessage.text = message
        btnYes.setOnClickListener {
            viewModel.deleteSeries(serie).let {
                Toast.makeText(context, serie.name + ", removida nos favoritos!", Toast.LENGTH_SHORT).show()
                fm.observeSeriesFirebase()
            }
            dialog.dismiss()
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun bind(item: SeriesFirebase) {
        Log.d("TESSSSTE", "bind: " + item.idFirebase)
        series = SeriesFirebase(
            item.id,
            item.poster_path ,
            item.name,
            item.userId,
            item.idFirebase
        )

        itemView.findViewById<TextView>(R.id.nomeOrTitle).text = item.name
        if ( item.poster_path.isNullOrEmpty() ) itemView.findViewById<ImageView>(R.id.imageViewMovieAndSeries).load(
            R.drawable.padrao)
        else itemView.findViewById<ImageView>(R.id.imageViewMovieAndSeries).load("https://image.tmdb.org/t/p/w500" + item.poster_path)
        itemView.findViewById<ConstraintLayout>(R.id.movieAndSeriesCellContainer).setOnClickListener {
            val intent = Intent( context, SerieDetailsActivity::class.java ).apply {
                putExtra("id", item.id.toString() )
            }
            context.startActivity(intent)
        }
        itemView.findViewById<ConstraintLayout>(R.id.movieAndSeriesCellContainer).setOnLongClickListener {
            val message: String? = "Voce tem certeza que deseja remover " + series!!.name + " de seus favoritos?"
            showCustomDialogBox(message, series!!)
            true
        }
    }
}