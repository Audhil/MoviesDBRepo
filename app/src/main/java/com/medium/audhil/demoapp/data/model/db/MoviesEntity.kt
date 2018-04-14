package com.medium.audhil.demoapp.data.model.db

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.medium.audhil.demoapp.data.local.db.AppDBNames

/*
 * Created by mohammed-2284 on 11/04/18.
 */

@Entity(
        tableName = AppDBNames.MOVIES_TABLE_NAME,
        indices = [(Index(value = ["title"], unique = true))]
)
data class MoviesEntity(
        @PrimaryKey(autoGenerate = true)
        var index: Int = 0,
        @SerializedName("total_results")
        var voteAverage: Float = 0f,
        @SerializedName("backdrop_path")
        var backdropPath: String? = null,
        @SerializedName("adult")
        var adult: Boolean = false,
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("title")
        var title: String? = null,
        @SerializedName("overview")
        var overview: String? = null,
        @SerializedName("original_language")
        var originalLanguage: String? = null,
        @SerializedName("release_date")
        var releaseDate: String? = null,
        @SerializedName("original_title")
        var originalTitle: String? = null,
        @SerializedName("vote_count")
        var voteCount: Int = 0,
        @SerializedName("poster_path")
        var posterPath: String? = null,
        @SerializedName("video")
        var video: Boolean = false,
        @SerializedName("popularity")
        var popularity: Float = 0f) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MoviesEntity

        if (index != other.index) return false
        if (voteAverage != other.voteAverage) return false
        if (backdropPath != other.backdropPath) return false
        if (adult != other.adult) return false
        if (id != other.id) return false
        if (title != other.title) return false
        if (overview != other.overview) return false
        if (originalLanguage != other.originalLanguage) return false
        if (releaseDate != other.releaseDate) return false
        if (originalTitle != other.originalTitle) return false
        if (voteCount != other.voteCount) return false
        if (posterPath != other.posterPath) return false
        if (video != other.video) return false
        if (popularity != other.popularity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = index
        result = 31 * result + voteAverage.hashCode()
        result = 31 * result + (backdropPath?.hashCode() ?: 0)
        result = 31 * result + adult.hashCode()
        result = 31 * result + id
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (overview?.hashCode() ?: 0)
        result = 31 * result + (originalLanguage?.hashCode() ?: 0)
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        result = 31 * result + (originalTitle?.hashCode() ?: 0)
        result = 31 * result + voteCount
        result = 31 * result + (posterPath?.hashCode() ?: 0)
        result = 31 * result + video.hashCode()
        result = 31 * result + popularity.hashCode()
        return result
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MoviesEntity> = object : Parcelable.Creator<MoviesEntity> {
            override fun createFromParcel(source: Parcel): MoviesEntity = MoviesEntity(source)
            override fun newArray(size: Int): Array<MoviesEntity?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readFloat(),
            source.readString(),
            1 == source.readInt(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readString(),
            1 == source.readInt(),
            source.readFloat()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(index)
        writeFloat(voteAverage)
        writeString(backdropPath)
        writeInt((if (adult) 1 else 0))
        writeInt(id)
        writeString(title)
        writeString(overview)
        writeString(originalLanguage)
        writeString(releaseDate)
        writeString(originalTitle)
        writeInt(voteCount)
        writeString(posterPath)
        writeInt((if (video) 1 else 0))
        writeFloat(popularity)
    }
}