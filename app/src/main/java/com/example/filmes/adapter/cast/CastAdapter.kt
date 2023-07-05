package com.example.filmes.adapter.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.databinding.CastCellBinding
import com.example.filmes.model.CastX

class CastAdapter (

    private val lista: List<CastX>
   // private val clickListener: CastClickListener

): RecyclerView.Adapter<CastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val from = LayoutInflater.from( parent.context )
        val binding = CastCellBinding.inflate( from, parent, false )

        return CastViewHolder( parent.context, binding )

    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bindSeason( lista[position] )
    }

    override fun getItemCount(): Int = lista.size

}