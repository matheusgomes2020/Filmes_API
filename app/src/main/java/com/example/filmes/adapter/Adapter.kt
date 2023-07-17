package com.example.filmes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class Adapter<T:BaseViewHolder<U>, U>(
    private val viewHolderLaunch: ( ViewGroup ) -> T,
   // private val clickListener: BaseClickListener<T>
    //private val context: Context,
) : RecyclerView.Adapter<T>() {

    var items: MutableList<U> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        return viewHolderLaunch( parent )
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        holder.bind( items[ position ] )
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

abstract class BaseViewHolder<U>(@LayoutRes layout: Int, viewGroup: ViewGroup ) : RecyclerView.ViewHolder(
    LayoutInflater.from( viewGroup.context).inflate( layout, viewGroup, false )
) {

    abstract fun bind( item: U )


}