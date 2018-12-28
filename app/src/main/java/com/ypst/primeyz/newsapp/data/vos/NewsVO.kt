package com.ypst.primeyz.newsapp.data.vos

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.ypst.primeyz.newsapp.persistence.typeconverters.DummySourceTypeConverter

/**
 * Created by yepyaesonetun on 11/24/18.
 **/

@Entity(tableName = "news")
@TypeConverters(DummySourceTypeConverter::class)
class NewsVO {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @SerializedName("author")
    var author: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("url")
    var url: String? = null
    @SerializedName("urlToImage")
    var urlToImage: String? = null
    @SerializedName("publishedAt")
    var publishedAt: String? = null
    @SerializedName("content")
    var content: String? = null
    @SerializedName("source")
    var sourceName: DummySourceVO? = null
}