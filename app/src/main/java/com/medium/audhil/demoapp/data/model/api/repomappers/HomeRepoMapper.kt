package com.medium.audhil.demoapp.data.model.api.repomappers

import com.google.gson.Gson
import com.medium.audhil.demoapp.data.model.api.generic.Mapper
import com.medium.audhil.demoapp.data.model.api.moviesdb.MoviesResponse
import com.medium.audhil.demoapp.data.model.db.MoviesEntity

/*
 * Created by mohammed-2284 on 12/04/18.
 */

class HomeRepoMapper
constructor(private val gson: Gson) : Mapper<Any?, Any?> {

    private val listOfMovies: MutableList<MoviesEntity> = mutableListOf()

    override fun map(input: Any?): Any? {
        (input as? MoviesResponse)?.results?.let {
            listOfMovies.clear()
            if (it.isNotEmpty()) {
                it.forEach {
                    listOfMovies.add(it)
                }
                return listOfMovies
            }
        }
        return null
    }
}