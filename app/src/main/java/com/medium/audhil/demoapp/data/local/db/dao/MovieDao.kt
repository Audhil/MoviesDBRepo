package com.medium.audhil.demoapp.data.local.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.medium.audhil.demoapp.data.local.db.AppDBNames
import com.medium.audhil.demoapp.data.local.db.dao.base.BaseDao
import com.medium.audhil.demoapp.data.model.db.MoviesEntity

/*
 * Created by mohammed-2284 on 11/04/18.
 */

@Dao
abstract class MovieDao : BaseDao() {
    @Query("select * from " + AppDBNames.MOVIES_TABLE_NAME)
    abstract fun getMovies(): LiveData<List<MoviesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(movie: MoviesEntity)

    @Query("DELETE FROM " + AppDBNames.MOVIES_TABLE_NAME)
    abstract override fun deleteTable()
}