package com.ypst.primeyz.newsapp.delegates

import com.ypst.primeyz.newsapp.mvp.presenters.MainPresenter

/**
 * Created by yepyaesonetun on 7/23/18.
 **/
interface HomePresenterDelegate {
    fun getPresenter(): MainPresenter
}