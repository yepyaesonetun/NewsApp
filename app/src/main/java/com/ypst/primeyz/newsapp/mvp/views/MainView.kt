package com.ypst.primeyz.newsapp.mvp.views

import com.ypst.primeyz.newsapp.data.vos.NewsVO
import com.ypst.primeyz.newsapp.data.vos.SourceVO

/**
 * Created by yepyaesonetun on 11/24/18.
 **/
interface MainView : BaseView{
    // home fragmennt
    fun displayHeadLineNewsDataList(dataList: List<NewsVO>)
    fun displayTodayDate(dateString: String)
    fun navigateToNewsSourceUrl(urlString: String)

    // headline fragment
    fun accessOnTappedCategoryName(categoryNameString: String)
    fun displayHeadLineNewsDatListByCategory(dataList: List<NewsVO>)

    fun navigateToSearchActivity()

    // news-stand fragment
    fun displayPopularSourceList(dataList: List<SourceVO>)
    fun navigateToNewsListBySourceID(sourceID: String, sourceName: String)
    fun navigateToAllSourceListActivity()
}