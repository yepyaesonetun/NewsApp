package com.ypst.primeyz.newsapp.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout

import com.ypst.primeyz.newsapp.R
import com.ypst.primeyz.newsapp.adapters.CategoryCardRVAdapter
import com.ypst.primeyz.newsapp.adapters.CategoryTextRVAdapter
import com.ypst.primeyz.newsapp.adapters.PopularSourceRVAdapter
import com.ypst.primeyz.newsapp.components.SmartRecyclerView
import com.ypst.primeyz.newsapp.data.vos.SourceVO
import com.ypst.primeyz.newsapp.delegates.HomePresenterDelegate
import com.ypst.primeyz.newsapp.mvp.presenters.MainPresenter
import com.ypst.primeyz.newsapp.utils.AppConstants


/**
 * A simple [Fragment] subclass.
 *
 */
class NewsStandFragment : Fragment() {

    private lateinit var mPresenter: MainPresenter
    private lateinit var mHomePresenterDelegate: HomePresenterDelegate
    private lateinit var mPopularSourceRVAdapter: PopularSourceRVAdapter
    private lateinit var mCategoryAdapter: CategoryCardRVAdapter

    private var categoryList: List<String> = listOf(
        AppConstants.CATEGORY_BUSSINESS,
        AppConstants.CATEGORY_ENTERTAINMENT,
        AppConstants.CATEGORY_GENERAL,
        AppConstants.CATEGORY_HEALTH,
        AppConstants.CATEGORY_SCIENCE,
        AppConstants.CATEGORY_SPORTS,
        AppConstants.CATEGORY_TECHNOLOTY
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news_stand, container, false)

        mPresenter = mHomePresenterDelegate.getPresenter() // parse down to adapter
        mPopularSourceRVAdapter = PopularSourceRVAdapter(context!!, mPresenter)
        mCategoryAdapter = CategoryCardRVAdapter(context!!, mPresenter)
        mCategoryAdapter.appendNewData(categoryList)

        val dividerDrawable = ContextCompat.getDrawable(context!!, R.drawable.line_divider)
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(dividerDrawable!!)


        val rvPSource: SmartRecyclerView = view.findViewById(R.id.rvPopularSource)
        rvPSource.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        rvPSource.setHasFixedSize(true)
        rvPSource.isNestedScrollingEnabled = false
        rvPSource.addItemDecoration(decoration)
        rvPSource.adapter = mPopularSourceRVAdapter

        val rvCatlog: SmartRecyclerView = view.findViewById(R.id.rvCategory)
        rvCatlog.layoutManager = GridLayoutManager(context,2)
        rvCatlog.setHasFixedSize(true)
        rvCatlog.isNestedScrollingEnabled = false
        rvCatlog.adapter = mCategoryAdapter


        val search: ImageView = view.findViewById(R.id.ivSearch)
        search.setOnClickListener { mPresenter.onTapSearch() }

        val flViewAll: FrameLayout  = view.findViewById(R.id.flViewMore)
        flViewAll.setOnClickListener { mPresenter.onTapViewAllSourceList() }

        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mHomePresenterDelegate = context as HomePresenterDelegate
    }

    fun addDataToAdapter(dataList: List<SourceVO>) {
        mPopularSourceRVAdapter.clearData()
        mPopularSourceRVAdapter.appendNewData(dataList)
    }

}
