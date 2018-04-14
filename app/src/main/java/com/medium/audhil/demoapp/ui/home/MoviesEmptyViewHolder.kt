package com.medium.audhil.demoapp.ui.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.medium.audhil.demoapp.R

/*
 * Created by mohammed-2284 on 14/04/18.
 */

class MoviesEmptyViewHolder(
        parent: ViewGroup
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.empty_view_item, parent, false))