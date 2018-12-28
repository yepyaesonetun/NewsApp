package com.ypst.primeyz.newsapp.data.models

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.ypst.primeyz.newsapp.data.vos.NewsVO
import com.ypst.primeyz.newsapp.network.response.NewsBaseResponse
import com.ypst.primeyz.newsapp.utils.AppConstants
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by yepyaesonetun on 11/24/18.
 **/
class NewsModel private constructor(context: Context) : BaseModel(context) {
    companion object {
        private var INSTANCE: NewsModel? = null
        fun getInstance(): NewsModel {
            if (INSTANCE == null) {
                throw RuntimeException("NewsModel is being invoke before initializing")
            }

            val objectInstance = INSTANCE
            return objectInstance!!
        }

        fun initNewsModel(context: Context) {
            INSTANCE = NewsModel(context)
        }
    }


    fun startLoadingTopHeadLine(
        countryCode: String, pageNo: Int,
        mNewsListLD: MutableLiveData<List<NewsVO>>, errorLD: MutableLiveData<String>
    ) {

        mTheAPI.getTopHeadLine(countryCode, AppConstants.NEWS_API_KEY, pageNo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<NewsBaseResponse> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(response: NewsBaseResponse) {
                    if (response.newsList!!.isNotEmpty()) {
                        mNewsListLD.value = response.newsList

                        // TODO persist to DB
                        persistToDB(response.newsList!!)
                    } else {
                        errorLD.value = "Empty List"
                    }
                }

                override fun onError(e: Throwable) {
                    errorLD.value = "Error ${e.localizedMessage}"
                }

            })
    }

    /**
     * Store to DB
     */
    private fun persistToDB(newsList: List<NewsVO>) {
        mTheDB.newsDao().insertNews(newsList)
    }

    /**
     * Get News list from DB
     * only used as cache while offline
     */
    fun getNewsListFromDB(): List<NewsVO> {
        return mTheDB.newsDao().allNewsList()
    }

    /**
     * get Single News Object from DB
     * just sample
     * app nature is Concurrency Network App that's why LiveData is quite Enough to use i think
     */
    fun getNewsByID(id: Int): NewsVO {
        return mTheDB.newsDao().getNewsById(id)
    }

    fun startLoadigHeadLineByCategory(
        countryCode: String, categoryNameString: String, pageNo: Int,
        mHeadLineByCategoryNameLD: MutableLiveData<List<NewsVO>>, errorLD: MutableLiveData<String>
    ) {
        mTheAPI.getHeadlinesByCategory(countryCode, categoryNameString, AppConstants.NEWS_API_KEY, pageNo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<NewsBaseResponse> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(response: NewsBaseResponse) {
                    if (response.newsList!!.isNotEmpty()) {
                        mHeadLineByCategoryNameLD.value = response.newsList
                    }
                }

                override fun onError(e: Throwable) {

                }

            })

    }

    fun startSearchQuery(
        searchKey: String, language: String,
        mSearchedDataListLD: MutableLiveData<List<NewsVO>>,
        errorLD: MutableLiveData<String>
    ) {
        mTheAPI.searchEverythingWithKeyword(searchKey, AppConstants.NEWS_API_KEY, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<NewsBaseResponse> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(response: NewsBaseResponse) {
                    if (response.newsList!!.isNotEmpty()) {
                        mSearchedDataListLD.value = response.newsList
                    }
                }

                override fun onError(e: Throwable) {

                }

            })
    }


    fun startLoadingNewsListBySourceId(
        sourceID: String,
        mNewsListBySourceIDLD: MutableLiveData<List<NewsVO>>,
        errorLD: MutableLiveData<String>
    ) {
        mTheAPI.getHeadLinesBySourceId(sourceID, AppConstants.NEWS_API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<NewsBaseResponse> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(response: NewsBaseResponse) {
                    if (response.newsList!!.isNotEmpty()) {
                        mNewsListBySourceIDLD.value = response.newsList
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