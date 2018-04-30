package com.medium.audhil.demoapp.ui.home

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.medium.audhil.demoapp.R
import com.medium.audhil.demoapp.data.model.db.MoviesEntity
import com.medium.audhil.demoapp.data.remote.MovieDBAppAPIEndPoints
import com.medium.audhil.demoapp.glide.GlideApp
import com.medium.audhil.demoapp.util.Callback

/*
 * Created by mohammed-2284 on 11/04/18.
 */

class MoviesViewHolder(
        parent: ViewGroup
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.movies_list_item, parent, false)) {

    private val timeSheetNameTxtView = itemView.findViewById<TextView>(R.id.movie_name)
    private val thumbnail = itemView.findViewById<ImageView>(R.id.movie_poster)
    private val baseCardView = itemView.findViewById<CardView>(R.id.movie_card_view)
    private var clickListener: Callback<Int>? = null

    //  bind to
    fun bindTo(pos: Int, movieEntity: MoviesEntity?, clickListener: Callback<Int>?) {
        this.clickListener = clickListener
        timeSheetNameTxtView.text = movieEntity?.title
        GlideApp.with(itemView.context)
                .load(MovieDBAppAPIEndPoints.IMAGE_END_POINT_W_500 +
                        movieEntity?.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round))
                .into(thumbnail)
        baseCardView.setOnClickListener {
            this.clickListener?.invoke(adapterPosition)
        }
    }
}