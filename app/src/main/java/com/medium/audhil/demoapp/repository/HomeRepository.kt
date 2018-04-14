package com.medium.audhil.demoapp.repository

import com.medium.audhil.demoapp.data.model.db.MoviesEntity
import com.medium.audhil.demoapp.data.remote.MovieDBAppAPIEndPoints
import com.medium.audhil.demoapp.repository.base.BaseRepository
import com.medium.audhil.demoapp.rx.makeSingleRxConnection
import com.medium.audhil.demoapp.util.Callback
import com.medium.audhil.demoapp.util.ConstantsUtil

/*
 * Created by mohammed-2284 on 11/04/18.
 */

class HomeRepository : BaseRepository() {

    var noMoreDataListener: Callback<Boolean>? = null

    override fun onSuccess(obj: Any?, tag: String) {
        when (tag) {
            MovieDBAppAPIEndPoints.MOVIES_DB_API ->
                mapper.map(obj)?.let {
                    (it as? MutableList<MoviesEntity>)?.let {
                        it.forEach {
                            appExecutors.diskIOThread().execute {
                                moviesDao.insert(it)
                            }
                        }
                    }
                }
        }
    }

    //  get reddits from DB
    fun getMoviesFromDB() = moviesDao.getMovies()

    //  fetching reddits
    fun fetchMovies(page: Int) =
            api.getTopRatedMovies(
                    apiKey = ConstantsUtil.MOVIES_DB_API_KEY,
                    page = page.toString())
                    .makeSingleRxConnection(this, MovieDBAppAPIEndPoints.MOVIES_DB_API)

    //  on refreshing
    fun deleteTableRows() =
            appExecutors.diskIOThread().execute {
                moviesDao.deleteTable()
            }
}