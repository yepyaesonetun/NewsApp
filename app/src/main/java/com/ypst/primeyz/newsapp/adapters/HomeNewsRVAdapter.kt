package com.ypst.primeyz.newsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ypst.primeyz.newsapp.R
import com.ypst.primeyz.newsapp.data.vos.NewsVO
import com.ypst.primeyz.newsapp.delegates.NewsItemDelegate
import com.ypst.primeyz.newsapp.viewholders.BaseViewHolder
import com.ypst.primeyz.newsapp.viewholders.HomeHeaderViewHolder
import com.ypst.primeyz.newsapp.viewholders.HomeNewsViewHolder

/**
 * Created by yepyaesonetun on 11/25/18.
 **/
class HomeNewsRVAdapter(context: Context, private val delegate: NewsItemDelegate) : BaseRecyclerAdapter<BaseViewHolder<NewsVO>,NewsVO>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<NewsVO> {
        return if(viewType == VT_HEADER){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_headline, parent, false)
            HomeHeaderViewHolder(view, delegate)
        }else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_headline_two, parent, false)
            HomeNewsViewHolder(view, delegate)
        }
    }

    private val VT_HEADER =  0
    private val VT_OTHER = 1

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VT_HEADER
            else -> VT_OTHER
        }
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }
}