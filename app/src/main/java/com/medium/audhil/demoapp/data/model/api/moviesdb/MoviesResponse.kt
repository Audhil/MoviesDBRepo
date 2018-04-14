package com.medium.audhil.demoapp.data.model.api.moviesdb

import com.google.gson.annotations.SerializedName
import com.medium.audhil.demoapp.data.model.db.MoviesEntity
import java.util.*

/*
 * Created by mohammed-2284 on 11/04/18.
 */

data class MoviesResponse(
        @SerializedName("results")
        var results: Array<MoviesEntity>? = null,
        @SerializedName("page")
        var page: Int,
        @SerializedName("total_pages")
        var totalPages: Int,
        @SerializedName("total_results")
        var totalResults: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MoviesResponse

        if (!Arrays.equals(results, other.results)) return false
        if (page != other.page) return false
        if (totalPages != other.totalPages) return false
        if (totalResults != other.totalResults) return false

        return true
    }

    //  this is auto generated - not MANDATORY
    override fun hashCode(): Int {
        var result = Arrays.hashCode(results)
        result = 31 * result + page.hashCode()
        result = 31 * result + totalPages.hashCode()
        result = 31 * result + totalResults.hashCode()
        return result
    }
}