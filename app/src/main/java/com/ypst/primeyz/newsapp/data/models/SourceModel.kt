package com.ypst.primeyz.newsapp.data.models

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.ypst.primeyz.newsapp.data.vos.SourceVO
import com.ypst.primeyz.newsapp.network.response.SourceResponse
import com.ypst.primeyz.newsapp.utils.AppConstants
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by yepyaesonetun on 11/24/18.
 **/
class SourceModel private constructor(context: Context) : BaseModel(context) {
    companion object {
        private var INSTANCE: SourceModel? = null
        fun getInstance(): SourceModel {
            if (INSTANCE == null) {
                throw RuntimeException("SourceModel is being invoke before initializing")
            }

            val i = INSTANCE
            return i!!
        }

        fun initSourceModel(context: Context) {
            INSTANCE = SourceModel(context)
        }
    }


    fun startLoadingSources(mSourceListLD: MutableLiveData<List<SourceVO>>, errorLD: MutableLiveData<String>) {
        mTheAPI.getAllSources(AppConstants.NEWS_API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<SourceResponse> {
                override fun onComplete() {

                }

                override fun onSubscribe(response: Disposable) {

                }

                override fun onNext(response: SourceResponse) {
                    if (response.sourceList!!.isNotEmpty() && response.status == "ok") {
                        mSourceListLD.value = response.sourceList
                    } else {
                        errorLD.value = "Empty List"
                    }
                }

                override fun onError(e: Throwable) {
                    errorLD.value = "Error ${e.localizedMessage}"
                }

            })
    }
}