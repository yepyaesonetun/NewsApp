package com.ypst.primeyz.newsapp.utils

import com.ypst.primeyz.newsapp.BuildConfig

/**
 * Created by yepyaesonetun on 11/24/18.
 **/
class AppConstants {
    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val INITIAL_PAGE_INDEX = 1
        const val CATEGORY_BUSSINESS = "business"
        const val CATEGORY_ENTERTAINMENT = "entertainment"
        const val CATEGORY_GENERAL = "general"
        const val CATEGORY_HEALTH = "health"
        const val CATEGORY_SCIENCE = "science"
        const val CATEGORY_SPORTS = "sports"
        const val CATEGORY_TECHNOLOTY = "technology"
        const val NEWS_API_KEY : String = BuildConfig.NEWS_API_KEY
    }
}