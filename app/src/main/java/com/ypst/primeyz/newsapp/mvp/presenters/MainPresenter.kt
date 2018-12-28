package com.ypst.primeyz.newsapp.mvp.presenters

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.ypst.primeyz.newsapp.data.models.NewsModel
import com.ypst.primeyz.newsapp.data.models.SourceModel
import com.ypst.primeyz.newsapp.data.vos.NewsVO
import com.ypst.primeyz.newsapp.data.vos.SourceVO
import com.ypst.primeyz.newsapp.delegates.CategoryItemDelegate
import com.ypst.primeyz.newsapp.delegates.NewsItemDelegate
import com.ypst.primeyz.newsapp.delegates.SourceItemDelegate
import com.ypst.primeyz.newsapp.mvp.views.MainView
import com.ypst.primeyz.newsapp.utils.AppConstants
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yepyaesonetun on 11/24/18.
 **/
class MainPresenter : BasePresenter<MainView>(), NewsItemDelegate, CategoryItemDelegate, SourceItemDelegate {
    override fun onTapSourceItem(sourceID: String, sourceName: String) {
        mView.navigateToNewsListBySourceID(sourceID, sourceName)
    }

    override fun onTapCategoryCard(categoryNameString: String) {
        mView.accessOnTappedCategoryName(categoryNameString)
    }

    override fun onTapNewsItem(urlToFullNews: String) {
        mView.navigateToNewsSourceUrl(urlToFullNews)
    }

    fun onTapSearch() {
        mView.navigateToSearchActivity()
    }

    fun onTapViewAllSourceList(){
        mView.navigateToAllSourceListActivity()
    }

    private var mHeadLineNewsVOListLD: MutableLiveData<List<NewsVO>>? = null
    private var mHeadLineNewsVOByCategoryLD: MutableLiveData<List<NewsVO>>? = null
    private var mPopularSourceListLD: MutableLiveData<List<SourceVO>>? = null

    override fun initPresenter(mView: MainView) {
        super.initPresenter(mView)
        mHeadLineNewsVOListLD = MutableLiveData()
        mHeadLineNewsVOByCategoryLD = MutableLiveData()
        mPopularSourceListLD = MutableLiveData()
    }

    fun onLoadTodayDate() {
        val c = Calendar.getInstance().time
        Log.e("D", "Current time => $c")

        val df = SimpleDateFormat("dd, MMMM yyyy")
        val formattedDate = df.format(c)
        mView.displayTodayDate(formattedDate)
    }

    fun onLoadHeadLineNewsData(countryCode: String, pageNo: Int) {
        NewsModel.getInstance().startLoadingTopHeadLine(countryCode, pageNo, mHeadLineNewsVOListLD!!, mErrorLD)
    }

    fun onForceReloadHeadLineNewsData(countryCode: String) {
        NewsModel.getInstance()
            .startLoadingTopHeadLine(countryCode, AppConstants.INITIAL_PAGE_INDEX, mHeadLineNewsVOListLD!!, mErrorLD)
    }

    val headLineNewsVOListLiveData: MutableLiveData<List<NewsVO>>
        get() = mHeadLineNewsVOListLD!!

    fun onLoadHeadLineDataListWithTappedCategoryName(countryCode: String, categoryNameString: String, pageNo: Int) {
        NewsModel.getInstance().startLoadigHeadLineByCategory(
            countryCode,
            categoryNameString,
            pageNo,
            mHeadLineNewsVOByCategoryLD!!,
            mErrorLD
        )
    }

    val headLineNewsVOListByCategoryLiveData: MutableLiveData<List<NewsVO>>
        get() = mHeadLineNewsVOByCategoryLD!!

    fun onLoadSourceList() {
        SourceModel.getInstance().startLoadingSources(mPopularSourceListLD!!, mErrorLD)
    }

    val popularSourceListLiveData: MutableLiveData<List<SourceVO>>
        get() = mPopularSourceListLD!!
}