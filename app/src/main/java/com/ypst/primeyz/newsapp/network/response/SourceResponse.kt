package com.ypst.primeyz.newsapp.network.response

import com.google.gson.annotations.SerializedName
import com.ypst.primeyz.newsapp.data.vos.SourceVO

/**
 * Created by yepyaesonetun on 11/24/18.
 **/
class SourceResponse {
    @SerializedName("status")
    var status: String? = null
    @SerializedName("sources")
    var sourceList: List<SourceVO>? = null
}