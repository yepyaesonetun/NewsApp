package com.ypst.primeyz.newsapp.mvp.presenters

import android.arch.lifecycle.MutableLiveData
import com.ypst.primeyz.newsapp.data.models.SourceModel
import com.ypst.primeyz.newsapp.data.vos.SourceVO
import com.ypst.primeyz.newsapp.delegates.SourceItemDelegate
import com.ypst.primeyz.newsapp.mvp.views.SourceListView

/**
 * Created by yepyaesonetun on 11/26/18.
 **/
class SourceListPresenter: BasePresenter<SourceListView>(), SourceItemDelegate {
    override fun onTapSourceItem(sourceID: String, sourceName: String) {
        mView.navigateToHeadLineBySourceID(sourceID, sourceName)
    }

    private var mSourceListLD: MutableLiveData<List<SourceVO>>? = null

    override fun initPresenter(mView: SourceListView) {
        super.initPresenter(mView)
        mSourceListLD = MutableLiveData()
    }

    fun onLoadSourceList() {
        SourceModel.getInstance().startLoadingSources(mSourceListLD!!, mErrorLD)
    }

    val sourceListLiveData: MutableLiveData<List<SourceVO>>
        get() = mSourceListLD!!
}