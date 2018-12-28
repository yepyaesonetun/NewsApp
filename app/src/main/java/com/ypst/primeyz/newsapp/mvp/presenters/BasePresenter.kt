package com.ypst.primeyz.newsapp.mvp.presenters

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ypst.primeyz.newsapp.mvp.views.BaseView

/**
 * Created by yepyaesonetun on 11/24/18.
 **/
abstract class BasePresenter<T : BaseView> : ViewModel() {

    protected lateinit var mView: T
    protected lateinit var mErrorLD: MutableLiveData<String>

    val errorLD: LiveData<String>
        get() = mErrorLD

    open fun initPresenter(mView: T) {
        this.mView = mView
        mErrorLD = MutableLiveData()
    }
}