package com.ypst.primeyz.newsapp.viewholders

import android.view.View
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.ypst.primeyz.newsapp.R
import com.ypst.primeyz.newsapp.data.vos.NewsVO
import com.ypst.primeyz.newsapp.delegates.NewsItemDelegate
import com.ypst.primeyz.newsapp.utils.GlideApp
import kotlinx.android.synthetic.main.item_news_headline.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yepyaesonetun on 11/25/18.
 **/
class HomeHeaderViewHolder(itemView: View, private val delegate: NewsItemDelegate): BaseViewHolder<NewsVO>(itemView) {

    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)

    override fun setData(data: NewsVO) {
        itemView.tvNewsTitle.text = data.title
        itemView.tvSourceName.text = data.sourceName!!.name
        GlideApp.with(itemView.ivNews.context)
            .load(data.urlToImage)
            .centerCrop()
            .placeholder(R.drawable.news_logo_placeholder)
            .into(itemView.ivNews)

        val date: Date = formatter.parse(data.publishedAt)
        val timeStamp = date.time
        itemView.tvPublishedDate.text = TimeAgo.using(timeStamp)

        itemView.setOnClickListener { delegate.onTapNewsItem(data.url!!) }

    }

    override fun onClick(v: View?) {

    }


}