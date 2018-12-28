package com.ypst.primeyz.newsapp.network

import com.ypst.primeyz.newsapp.network.response.NewsBaseResponse
import com.ypst.primeyz.newsapp.network.response.SourceResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by yepyaesonetun on 11/24/18.
 **/

interface NewsAPI {
    @GET("top-headlines")
    fun getTopHeadLine(
        @Query("country") countryCode: String, @Query("apiKey") apiKey: String,
        @Query("page") pageNo: Int
    ): Observable<NewsBaseResponse>

    @GET("top-headlines")
    fun getHeadlinesByCategory(
        @Query("country") countryCode: String, @Query("category") categoryNameString: String,
        @Query("apiKey") apiKey: String, @Query("page") pageNo: Int
    ): Observable<NewsBaseResponse>

    @GET("everything")
    fun searchEverythingWithKeyword(
        @Query("q") keyword: String, @Query("apiKey") apiKey: String,
        @Query("language") language: String
    ): Observable<NewsBaseResponse>

    @GET("sources")
    fun getAllSources(@Query("apiKey") apiKey: String): Observable<SourceResponse>

    @GET("top-headlines")
    fun getHeadLinesBySourceId(@Query("sources") sourceID: String, @Query("apiKey") apiKey: String): Observable<NewsBaseResponse>
}