package com.medium.audhil.demoapp.data.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.medium.audhil.demoapp.data.local.db.AppDBNames.DB_NAME
import com.medium.audhil.demoapp.data.local.db.dao.MovieDao
import com.medium.audhil.demoapp.data.model.db.MoviesEntity

/*
 * Created by mohammed-2284 on 11/04/18.
 */

@Database(
        entities = [(MoviesEntity::class)],
        version = 1,
        exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        const val dbName = DB_NAME
    }

    abstract fun moviesDao(): MovieDao
}

//  DB & TableNames
object AppDBNames {
    const val DB_NAME = "AppDataBase.db"

    const val MOVIES_TABLE_NAME = "MoviesTable"
}