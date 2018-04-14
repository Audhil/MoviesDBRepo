package com.medium.audhil.demoapp.ui.home

import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.medium.audhil.demoapp.R
import com.medium.audhil.demoapp.data.model.db.MoviesEntity
import com.medium.audhil.demoapp.data.remote.MovieDBAppAPIEndPoints
import com.medium.audhil.demoapp.databinding.MoviesGridItemBinding
import com.medium.audhil.demoapp.util.Callback

/*
 * Created by mohammed-2284 on 12/04/18.
 */

class MoviesGridViewHolder(
        val binding: MoviesGridItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var clickListener: Callback<Int>? = null

    //  bind to
    fun bindTo(pos: Int, movieEntity: MoviesEntity?, clickListener: Callback<Int>?) {
        this.clickListener = clickListener

        binding.movieName.text = movieEntity?.title

        Glide.with(itemView.context)
                .load(MovieDBAppAPIEndPoints.IMAGE_END_POINT_W_500 +
                        movieEntity?.posterPath)
                .apply(RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round))
                .into(binding.moviePoster)

        itemView.setOnClickListener {
            this.clickListener?.invoke(adapterPosition)
        }
    }
}