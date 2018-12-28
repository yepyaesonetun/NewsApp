package com.ypst.primeyz.newsapp.persistence.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.ypst.primeyz.newsapp.data.vos.NewsVO

/**
 * Created by yepyaesonetun on 11/25/18.
 **/
@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun allNewsList() : List<NewsVO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(healthCareInfoVOs : List<NewsVO>) : LongArray

    @Query("SELECT * FROM news WHERE id = :id")
    fun getNewsById(id: Int): NewsVO

    @Query("SELECT * FROM news WHERE id = :id")
    fun getNewsByIdLD(id : Int) : NewsVO
}