package com.ypst.primeyz.newsapp.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.ypst.primeyz.newsapp.data.vos.NewsVO
import com.ypst.primeyz.newsapp.persistence.dao.NewsDao

/**
 * Created by yepyaesonetun on 11/25/18.
 **/

@Database(entities = [(NewsVO::class)], version = 1, exportSchema = false)
abstract class NewsDB : RoomDatabase() {
    companion object {
        val DB_NAME: String = "YZ_TODAY_NEWS.DB"
        var DB_INSTANCE: NewsDB? = null
        fun getDatabase(context: Context): NewsDB {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(context, NewsDB::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
            val i = DB_INSTANCE
            return i!!
        }
    }

    // define DAOs
    abstract fun newsDao(): NewsDao
}