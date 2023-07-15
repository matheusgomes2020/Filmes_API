package com.example.filmes.adapter.guest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.databinding.CastCellBinding
import com.example.filmes.di.GuestStarX
import com.example.filmes.model.CastX

class GuestAdapter (

    private val lista: List<GuestStarX>,
    private val clickListener: GuestClickListener

): RecyclerView.Adapter<GuestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val from = LayoutInflater.from( parent.context )
        val binding = CastCellBinding.inflate( from, parent, false )

        return GuestViewHolder( parent.context, binding, clickListener )

    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        holder.bindCast( lista[position] )
    }

    override fun getItemCount(): Int = lista.size

}