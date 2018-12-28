package com.ypst.primeyz.newsapp.mvp.views

import com.ypst.primeyz.newsapp.data.vos.NewsVO

/**
 * Created by yepyaesonetun on 11/25/18.
 **/
interface EverythingSearchView : BaseView {
    fun finishActivity()
    fun displayResultList(resultlist: List<NewsVO>)
    fun navigateToNewsSourceUrl(urlString: String)
}