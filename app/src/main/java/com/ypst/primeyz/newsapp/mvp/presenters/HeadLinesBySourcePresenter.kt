package com.ypst.primeyz.newsapp.mvp.presenters

import android.arch.lifecycle.MutableLiveData
import com.ypst.primeyz.newsapp.data.models.NewsModel
import com.ypst.primeyz.newsapp.data.vos.NewsVO
import com.ypst.primeyz.newsapp.delegates.NewsItemDelegate
import com.ypst.primeyz.newsapp.mvp.views.HeadLinesBySourceView

/**
 * Created by yepyaesonetun on 11/26/18.
 **/
class HeadLinesBySourcePresenter : BasePresenter<HeadLinesBySourceView>(), NewsItemDelegate {

    private var mHeadLineNewsVOListLD: MutableLiveData<List<NewsVO>>? = null

    override fun onTapNewsItem(urlToFullNews: String) {
        mView.navigateToFullUrl(urlToFullNews)
    }

    override fun initPresenter(mView: HeadLinesBySourceView) {
        super.initPresenter(mView)
        mHeadLineNewsVOListLD = MutableLiveData()
    }

    fun onLoadHeadLineNewsData(sourceName: String) {
        NewsModel.getInstance().startLoadingNewsListBySourceId(sourceName, mHeadLineNewsVOListLD!!, mErrorLD)
    }

    val headLineNewsVOListLiveData: MutableLiveData<List<NewsVO>>
        get() = mHeadLineNewsVOListLD!!
}