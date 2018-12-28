package com.ypst.primeyz.newsapp.data.vos

import com.google.gson.annotations.SerializedName

/**
 * Created by yepyaesonetun on 11/24/18.
 **/
class SourceVO {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("url")
    var url: String? = null
    @SerializedName("category")
    var category: String? = null
    @SerializedName("language")
    var language: String? = null
    @SerializedName("country")
    var country: String? = null
}