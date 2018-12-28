package com.ypst.primeyz.newsapp.viewholders

import android.view.View
import com.ypst.primeyz.newsapp.data.vos.SourceVO
import com.ypst.primeyz.newsapp.delegates.SourceItemDelegate
import kotlinx.android.synthetic.main.item_source.view.*

/**
 * Created by yepyaesonetun on 11/26/18.
 **/
class SourceViewHolder(itemView: View, private val delegate: SourceItemDelegate) : BaseViewHolder<SourceVO>(itemView) {
    override fun setData(data: SourceVO) {
        itemView.tvSourceName.text = data.name
        itemView.tvSourceWebsite.text = data.url

        itemView.setOnClickListener { delegate.onTapSourceItem(data.id!!, data.name!!) }
    }

    override fun onClick(v: View?) {

    }
}