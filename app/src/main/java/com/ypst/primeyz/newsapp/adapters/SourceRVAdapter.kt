package com.ypst.primeyz.newsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ypst.primeyz.newsapp.R
import com.ypst.primeyz.newsapp.data.vos.SourceVO
import com.ypst.primeyz.newsapp.delegates.SourceItemDelegate
import com.ypst.primeyz.newsapp.viewholders.BaseViewHolder
import com.ypst.primeyz.newsapp.viewholders.SourceViewHolder

/**
 * Created by yepyaesonetun on 11/26/18.
 **/
class SourceRVAdapter(context: Context, private val delegate: SourceItemDelegate) : BaseRecyclerAdapter<SourceViewHolder, SourceVO>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SourceVO> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_source, parent, false)
        return SourceViewHolder(view, delegate)
    }

}