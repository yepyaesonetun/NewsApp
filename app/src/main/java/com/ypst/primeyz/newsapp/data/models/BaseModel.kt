package com.ypst.primeyz.newsapp.data.models

import android.content.Context
import com.google.gson.Gson
import com.ypst.primeyz.newsapp.network.NewsAPI
import com.ypst.primeyz.newsapp.persistence.NewsDB
import com.ypst.primeyz.newsapp.utils.AppConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by yepyaesonetun on 11/24/18.
 **/
abstract class BaseModel protected constructor(context: Context) {
    protected var mTheAPI : NewsAPI
    protected var mTheDB: NewsDB
    init{
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        mTheAPI = retrofit.create(NewsAPI::class.java)
        mTheDB = NewsDB.getDatabase(context)
    }
}