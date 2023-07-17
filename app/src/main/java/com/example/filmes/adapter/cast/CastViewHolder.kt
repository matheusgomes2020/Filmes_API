package com.example.filmes.adapter.cast

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.R
import com.example.filmes.databinding.CastCellBinding
import com.example.filmes.model.CastX

class CastViewHolder (

    private val context: Context,
    private val binding: CastCellBinding,
    private val clickListener: CastClickListener

    ): RecyclerView.ViewHolder( binding.root ) {

        fun bindCast(cast: CastX ) {

            if (cast.profile_path == null ) {

                binding.imageCast.load(R.drawable.padrao)

            } else {

                binding.imageCast.load("https://image.tmdb.org/t/p/w500" + cast.profile_path)


            }

            binding.textActorName.text = cast.name


            binding.containerCast.setOnClickListener {

               clickListener.clickCast( cast )

            }

        }

    }
