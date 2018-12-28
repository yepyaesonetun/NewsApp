package com.ypst.primeyz.newsapp.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.ypst.primeyz.newsapp.R
import com.ypst.primeyz.newsapp.adapters.HomeNewsRVAdapter
import com.ypst.primeyz.newsapp.components.SmartRecyclerView
import com.ypst.primeyz.newsapp.data.models.NewsModel
import com.ypst.primeyz.newsapp.data.vos.NewsVO
import com.ypst.primeyz.newsapp.delegates.HomePresenterDelegate
import com.ypst.primeyz.newsapp.mvp.presenters.MainPresenter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    override fun onRefresh() {
        srl!!.isRefreshing = false
        mPresenter.onForceReloadHeadLineNewsData("us")
        mAdapter.clearData()
    }

    private lateinit var mPresenter: MainPresenter
    private lateinit var mHomePresenterDelegate: HomePresenterDelegate
    private lateinit var mAdapter: HomeNewsRVAdapter
    private var mDateString: String = ""
    private var srl: SwipeRefreshLayout? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        srl = view.findViewById(R.id.srlHomeFragment)
        srl!!.setOnRefreshListener(this)

        mPresenter = mHomePresenterDelegate.getPresenter()  // parse down to adapter
        mAdapter = HomeNewsRVAdapter(context!!, mPresenter)

        mAdapter.appendNewData(NewsModel.getInstance().getNewsListFromDB()) // for offline state

        val tvDate: TextView = view.findViewById(R.id.tvTodayDate)
        tvDate.text = mDateString

        val dividerDrawable = ContextCompat.getDrawable(context!!, R.drawable.line_divider)
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(dividerDrawable!!)

        val rv: SmartRecyclerView = view.findViewById(R.id.rvNews)
        rv.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        rv.setHasFixedSize(true)
        rv.addItemDecoration(decoration)
        rv.adapter = mAdapter

        val search: ImageView = view.findViewById(R.id.ivSearch)
        search.setOnClickListener { mPresenter.onTapSearch() }


        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mHomePresenterDelegate = context as HomePresenterDelegate
    }

    fun addDataToAdapter(newsList: List<NewsVO>) {
        mAdapter.clearData()
        mAdapter.appendNewData(newsList)
    }

    fun displayTodayDate(dateString: String) {
        mDateString = dateString
    }

}
