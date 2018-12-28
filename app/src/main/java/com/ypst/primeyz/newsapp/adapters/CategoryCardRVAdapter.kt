package com.ypst.primeyz.newsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ypst.primeyz.newsapp.R
import com.ypst.primeyz.newsapp.delegates.CategoryItemDelegate
import com.ypst.primeyz.newsapp.viewholders.BaseViewHolder
import com.ypst.primeyz.newsapp.viewholders.CategoryCardViewHolder
import com.ypst.primeyz.newsapp.viewholders.CategoryTextViewHolder

/**
 * Created by yepyaesonetun on 11/25/18.
 **/
class CategoryCardRVAdapter(context: Context, private val delegate: CategoryItemDelegate): BaseRecyclerAdapter<CategoryTextViewHolder, String>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_categroy_card, parent, false)
        return CategoryCardViewHolder(itemView = view, delegate = delegate)
    }

}