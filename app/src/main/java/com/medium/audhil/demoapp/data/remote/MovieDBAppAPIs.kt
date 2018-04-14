package com.medium.audhil.demoapp.data.remote

import com.medium.audhil.demoapp.data.model.api.moviesdb.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * Created by mohammed-2284 on 12/04/18.
 */

interface MovieDBAppAPIs {

    @GET(MovieDBAppAPIEndPoints.MOVIES_DB_API)
    fun getTopRatedMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String = "en-US",
            @Query("page") page: String,
            @Query("limit") limit: String = "20"    //  limit is optional parameter
    ): Single<MoviesResponse>
}