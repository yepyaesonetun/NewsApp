package com.ypst.primeyz.newsapp.network.response

import com.google.gson.annotations.SerializedName
import com.ypst.primeyz.newsapp.data.vos.NewsVO

/**
 * Created by yepyaesonetun on 11/24/18.
 **/
class NewsBaseResponse {
    @SerializedName("status")
    var status: String? = null
    @SerializedName("totalResults")
    var totalResults: Int? = null
    @SerializedName("articles")
    var newsList: List<NewsVO>? = null
}