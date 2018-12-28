package com.ypst.primeyz.newsapp.mvp.views

import com.ypst.primeyz.newsapp.data.vos.SourceVO

/**
 * Created by yepyaesonetun on 11/26/18.
 **/
interface SourceListView: BaseView {
    fun displaySourceList(list: List<SourceVO>)
    fun navigateToHeadLineBySourceID(sourceID: String, sourceName: String)
}