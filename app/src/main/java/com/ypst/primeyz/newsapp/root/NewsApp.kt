package com.ypst.primeyz.newsapp.root

import android.app.Application
import com.ypst.primeyz.newsapp.data.models.NewsModel
import com.ypst.primeyz.newsapp.data.models.SourceModel
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric



/**
 * Created by yepyaesonetun on 11/24/18.
 **/


class NewsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Fabric.with(this, Crashlytics())
        NewsModel.initNewsModel(context = this)
        SourceModel.initSourceModel(context = this)
    }
}