package com.ypst.primeyz.newsapp.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.ypst.primeyz.newsapp.R
import com.ypst.primeyz.newsapp.adapters.CategoryTextRVAdapter
import com.ypst.primeyz.newsapp.adapters.HeadLineNewsRVAdapter
import com.ypst.primeyz.newsapp.components.SmartRecyclerView
import com.ypst.primeyz.newsapp.data.models.NewsModel
import com.ypst.primeyz.newsapp.data.vos.NewsVO
import com.ypst.primeyz.newsapp.delegates.HomePresenterDelegate
import com.ypst.primeyz.newsapp.mvp.presenters.MainPresenter
import com.ypst.primeyz.newsapp.utils.AppConstants

/**
 * A simple [Fragment] subclass.
 *
 */
class HeadLinesFragment : Fragment(){

    private lateinit var mPresenter: MainPresenter
    private lateinit var mHomePresenterDelegate: HomePresenterDelegate
    private lateinit var mCategoryAdapter: CategoryTextRVAdapter
    private lateinit var mNewsAdapter : HeadLineNewsRVAdapter
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
        val view = inflater.inflate(R.layout.fragment_head_lines, container, false)

        mPresenter = mHomePresenterDelegate.getPresenter() // parse down to adapter
        mCategoryAdapter = CategoryTextRVAdapter(context!!, mPresenter)
        mCategoryAdapter.appendNewData(categoryList)

        mNewsAdapter = HeadLineNewsRVAdapter(context!!, mPresenter)
        mNewsAdapter.appendNewData(NewsModel.getInstance().getNewsListFromDB()) // for offline state

        val dividerDrawable = ContextCompat.getDrawable(context!!, R.drawable.line_divider)
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(dividerDrawable!!)

        val rvCategory: SmartRecyclerView = view.findViewById(R.id.rvHeadLinesCategory)
        rvCategory.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        rvCategory.setHasFixedSize(true)
        rvCategory.isNestedScrollingEnabled = false
        rvCategory.adapter = mCategoryAdapter

        val rv: SmartRecyclerView = view.findViewById(R.id.rvNews)
        rv.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        rv.setHasFixedSize(true)
        rv.isNestedScrollingEnabled = false
        rv.adapter = mNewsAdapter

        val search: ImageView = view.findViewById(R.id.ivSearch)
        search.setOnClickListener { mPresenter.onTapSearch() }

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mHomePresenterDelegate = context as HomePresenterDelegate
    }

    fun addDataToAdapter(dataList: List<NewsVO>){
        mNewsAdapter.clearData()
        mNewsAdapter.appendNewData(dataList)
    }

}
