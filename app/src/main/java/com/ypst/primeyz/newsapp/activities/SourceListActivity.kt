package com.ypst.primeyz.newsapp.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.ypst.primeyz.newsapp.R
import com.ypst.primeyz.newsapp.adapters.SourceRVAdapter
import com.ypst.primeyz.newsapp.data.vos.SourceVO
import com.ypst.primeyz.newsapp.mvp.presenters.SourceListPresenter
import com.ypst.primeyz.newsapp.mvp.views.SourceListView
import kotlinx.android.synthetic.main.activity_source_list.*

class SourceListActivity : BaseActivity(), SourceListView {
    override fun navigateToHeadLineBySourceID(sourceID: String, sourceName: String) {
        startActivity(HeadLinesBySourceActivity.getIntent(this, sourceID, sourceName))
    }

    override fun displaySourceList(list: List<SourceVO>) {
        mAdapter.clearData()
        mAdapter.appendNewData(list)
    }

    private lateinit var mPresenter: SourceListPresenter
    private lateinit var mAdapter: SourceRVAdapter

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SourceListActivity::class.java)
        }
    }

    override fun setUpContents(savedInstanceState: Bundle?) {
        setUpToolbar(false)
        setUpToolbarText("All Sources List")

        mPresenter = ViewModelProviders.of(this)
            .get(SourceListPresenter::class.java)
        mPresenter.initPresenter(this)

        mAdapter  = SourceRVAdapter(this,mPresenter)

        val dividerDrawable = ContextCompat.getDrawable(this, R.drawable.line_divider)
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(dividerDrawable!!)

        rvSources.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rvSources.setHasFixedSize(true)
        rvSources.isNestedScrollingEnabled = false
        rvSources.addItemDecoration(decoration)
        rvSources.adapter = mAdapter

        mPresenter.onLoadSourceList()
        observeSourceList()

        mPresenter.errorLD.observe(this, Observer<String> { errorMsg ->
            Log.e("count", "$errorMsg")
        })

    }

    private fun observeSourceList() {
        mPresenter.sourceListLiveData.observe(this,
            Observer<List<SourceVO>> { sourceList: List<SourceVO>? ->
                displaySourceList(sourceList!!)
            })
    }

    override fun getlayoutRes(): Int {
        return R.layout.activity_source_list
    }

}
