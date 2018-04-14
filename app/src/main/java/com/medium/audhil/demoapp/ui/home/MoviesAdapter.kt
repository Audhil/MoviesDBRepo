package com.medium.audhil.demoapp.ui.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import com.medium.audhil.demoapp.R
import com.medium.audhil.demoapp.data.model.db.MoviesEntity
import com.medium.audhil.demoapp.databinding.*
import com.medium.audhil.demoapp.ui.other.loading.LoadingItemViewHolder
import com.medium.audhil.demoapp.util.Callback
import com.medium.audhil.demoapp.util.ConstantsUtil

/*
 * Created by mohammed-2284 on 13/04/18.
 */

class MoviesAdapter(val tag: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var moviesList: MutableList<MoviesEntity?> = mutableListOf()

    var clickListener: Callback<Int>? = null

    override fun getItemCount(): Int = if (moviesList.size > 0) moviesList.size else 1  //  to show an empty view

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
            when (holder) {
                is MoviesViewHolder ->
                    holder.bindTo(position, moviesList[position], clickListener)

                is MoviesGridViewHolder -> {
                    holder.bindTo(position, moviesList[position], clickListener)
                    setAnimation(holder.binding.movieCardView, position)
                }

                is MoviesLinearViewHolder ->
                    holder.bindTo(position, moviesList[position], clickListener)

                else ->
                    Unit    //  do nothing
            }


    //  set alpha animation
    private fun setAnimation(itemView: View, position: Int) {
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1000
        itemView.startAnimation(alphaAnimation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when (viewType) {
                ConstantsUtil.VIEW_TYPE_LOADING ->
                    //  loading_item.xml
                    LoadingItemViewHolder(parent)

                ConstantsUtil.VIEW_TYPE_EMPTY ->
                    //  empty_view_item.xml
                    MoviesEmptyViewHolder(parent)

                else ->
                    when (tag) {

                        parent.context.getString(R.string.nav_staggered_grid) ->
                            //  movies_grid_item.xml
                            MoviesGridViewHolder(MoviesGridItemBinding.inflate(LayoutInflater.from(parent.context)))

                        parent.context.getString(R.string.nav_grid) ->
                            //  movies_grid_item.xml
                            MoviesGridViewHolder(MoviesGridItemBinding.inflate(LayoutInflater.from(parent.context)))

                        parent.context.getString(R.string.nav_linear) ->
                            //  movies_linear_item.xml
                            MoviesLinearViewHolder(parent)

                        else ->
                            //  movies_list_item.xml
                            MoviesViewHolder(parent)
                    }
            }

    override fun getItemViewType(position: Int): Int =
            when {
                moviesList.size == 0 ->
                    ConstantsUtil.VIEW_TYPE_EMPTY

                moviesList[position] != null ->
                    ConstantsUtil.VIEW_TYPE_ITEM

                else ->
                    ConstantsUtil.VIEW_TYPE_LOADING
            }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) =
            when (holder) {
                is MoviesViewHolder ->
                    holder.itemView.clearAnimation()

                is MoviesGridViewHolder ->
                    holder.binding.movieCardView.clearAnimation()

                is MoviesLinearViewHolder ->
                    holder.itemView.clearAnimation()

                else ->
                    Unit    //  do nothing
            }

    fun getItemAtPosition(pos: Int): MoviesEntity? = moviesList[pos]

    //  removing loading view
    fun removeLoadingView() {
        moviesList.remove(null)
        notifyDataSetChanged()
    }

    //  adding movies to the list
    fun addMovies(posts: List<MoviesEntity>?, isToolbarSearchViewOpen: Boolean = false) =
            posts?.let {
                this.moviesList.clear()
                this.moviesList.addAll(it)
                //  don't add loading view at bottom if search view is open
                if (!isToolbarSearchViewOpen)
                    this.moviesList.add(null)
                notifyDataSetChanged()
            }
}