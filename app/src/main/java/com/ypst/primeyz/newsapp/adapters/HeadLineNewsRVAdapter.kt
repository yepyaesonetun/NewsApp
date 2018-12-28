package com.ypst.primeyz.newsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ypst.primeyz.newsapp.R
import com.ypst.primeyz.newsapp.data.vos.NewsVO
import com.ypst.primeyz.newsapp.delegates.NewsItemDelegate
import com.ypst.primeyz.newsapp.viewholders.BaseViewHolder
import com.ypst.primeyz.newsapp.viewholders.HeadLineNewsViewHolder

/**
 * Created by yepyaesonetun on 11/25/18.
 **/
class HeadLineNewsRVAdapter(context: Context, private val delegate: NewsItemDelegate) : BaseRecyclerAdapter<HeadLineNewsViewHolder, NewsVO>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<NewsVO> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_headline_in_headline_fragment, parent, false)
        return HeadLineNewsViewHolder(view, delegate)
    }
}