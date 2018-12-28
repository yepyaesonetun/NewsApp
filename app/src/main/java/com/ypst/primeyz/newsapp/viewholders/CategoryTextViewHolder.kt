package com.ypst.primeyz.newsapp.viewholders

import android.view.View
import com.ypst.primeyz.newsapp.delegates.CategoryItemDelegate
import kotlinx.android.synthetic.main.item_categroy_text_card.view.*

/**
 * Created by yepyaesonetun on 11/25/18.
 **/
class CategoryTextViewHolder(itemView: View, private val delegate: CategoryItemDelegate) : BaseViewHolder<String>(itemView) {
    override fun setData(data: String) {
        itemView.tvCategoryName.text = data

        itemView.setOnClickListener { delegate.onTapCategoryCard(data) }
    }

    override fun onClick(v: View?) {

    }

}