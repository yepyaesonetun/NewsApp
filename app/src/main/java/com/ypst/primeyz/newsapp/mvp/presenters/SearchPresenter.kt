package com.ypst.primeyz.newsapp.mvp.presenters

import android.arch.lifecycle.MutableLiveData
import com.ypst.primeyz.newsapp.data.models.NewsModel
import com.ypst.primeyz.newsapp.data.vos.NewsVO
import com.ypst.primeyz.newsapp.delegates.NewsItemDelegate
import com.ypst.primeyz.newsapp.mvp.views.EverythingSearchView

/**
 * Created by yepyaesonetun on 11/25/18.
 **/
class SearchPresenter : BasePresenter<EverythingSearchView>() , NewsItemDelegate{
    override fun onTapNewsItem(urlToFullNews: String) {
        mView.navigateToNewsSourceUrl(urlToFullNews)
    }

    override fun initPresenter(mView: EverythingSearchView) {
        super.initPresenter(mView)
        mResultDataListLiveData = MutableLiveData()
    }

    fun onTapBack() {
        mView.finishActivity()

    }

    private var mResultDataListLiveData: MutableLiveData<List<NewsVO>>? = null

    fun onActionSearch(searchKey: String, language: String) {
        NewsModel.getInstance().startSearchQuery(searchKey, language, mResultDataListLiveData!!, mErrorLD)
    }

    val resultListLiveData: MutableLiveData<List<NewsVO>>
        get() = mResultDataListLiveData!!
}