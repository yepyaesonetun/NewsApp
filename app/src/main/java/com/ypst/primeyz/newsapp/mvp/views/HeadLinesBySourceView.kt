package com.ypst.primeyz.newsapp.mvp.views

import com.ypst.primeyz.newsapp.data.vos.NewsVO

/**
 * Created by yepyaesonetun on 11/26/18.
 **/
interface HeadLinesBySourceView : BaseView{
    fun displayNewsList(newsLit: List<NewsVO>)
    fun navigateToFullUrl(urlString: String)
}