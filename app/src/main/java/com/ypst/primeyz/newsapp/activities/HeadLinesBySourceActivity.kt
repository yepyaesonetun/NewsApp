package com.ypst.primeyz.newsapp.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.ypst.primeyz.newsapp.R
import com.ypst.primeyz.newsapp.adapters.HeadLineNewsRVAdapter
import com.ypst.primeyz.newsapp.data.vos.NewsVO
import com.ypst.primeyz.newsapp.mvp.presenters.HeadLinesBySourcePresenter
import com.ypst.primeyz.newsapp.mvp.views.HeadLinesBySourceView
import kotlinx.android.synthetic.main.activity_head_lines_by_source.*
import saschpe.android.customtabs.CustomTabsHelper
import saschpe.android.customtabs.WebViewFallback

class HeadLinesBySourceActivity : BaseActivity(), HeadLinesBySourceView {

    companion object {
        private const val IE_SOURCE_ID = "IE_SOURCE_ID"
        private const val IE_SOURCE_NAME = "IE_SOURCE_NAME"
        fun getIntent(context: Context, sourceID: String, sourceName: String): Intent {
            val intent = Intent(context, HeadLinesBySourceActivity::class.java)
            intent.putExtra(IE_SOURCE_ID, sourceID)
            intent.putExtra(IE_SOURCE_NAME, sourceName)
            return intent
        }
    }

    override fun displayNewsList(newsList: List<NewsVO>) {
        mAdapter.clearData()
        mAdapter.appendNewData(newsList)
    }

    override fun navigateToFullUrl(urlString: String) {
        startLoadUrl(urlString)
    }

    private lateinit var mPresenter: HeadLinesBySourcePresenter
    private lateinit var mAdapter: HeadLineNewsRVAdapter

    override fun setUpContents(savedInstanceState: Bundle?) {
        setUpToolbar(false)
        setUpToolbarText("Explore in")

        tvSourceName.text = intent.getStringExtra(IE_SOURCE_NAME)

        mPresenter = ViewModelProviders.of(this).get(HeadLinesBySourcePresenter::class.java)
        mPresenter.initPresenter(this)

        mAdapter = HeadLineNewsRVAdapter(this, mPresenter)

        val dividerDrawable = ContextCompat.getDrawable(this, R.drawable.line_divider)
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(dividerDrawable!!)

        rvHeadLines.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rvHeadLines.setHasFixedSize(true)
        rvHeadLines.isNestedScrollingEnabled = false
        rvHeadLines.addItemDecoration(decoration)
        rvHeadLines.adapter = mAdapter


        mPresenter.onLoadHeadLineNewsData(intent.getStringExtra(IE_SOURCE_ID))
        observeNewsDataList()

        mPresenter.errorLD.observe(this, Observer<String> { errorMsg ->
            Log.e("count", "$errorMsg")
        })

    }

    private fun observeNewsDataList() {
        mPresenter.headLineNewsVOListLiveData.observe(this,
            Observer<List<NewsVO>> { newsVOList: List<NewsVO>? ->
                displayNewsList(newsVOList!!)
            })
    }

    override fun getlayoutRes(): Int {
        return R.layout.activity_head_lines_by_source
    }


    /**
     * @param webUrl : parse down website url to load with custom tab
     */
    private fun startLoadUrl(webUrl: String) {
        // Apply some fancy animation to show off
        val customTabsIntent = getDefaultCustomTabsIntentBuilder()
            .setExitAnimations(this, R.anim.slide_in_left, R.anim.slide_out_right)
            .build()

        CustomTabsHelper.addKeepAliveExtra(this, customTabsIntent.intent)

        CustomTabsHelper.openCustomTab(
            this, customTabsIntent,
            Uri.parse(webUrl),
            WebViewFallback()
        )
    }

    /**
     * Apply some sane defaults across a single app.
     *
     *
     * Not strictly necessary but simplifies code when having many different
     * custom tab intents in one app.
     *
     * @return [CustomTabsIntent.Builder] with defaults already applied
     */
    private fun getDefaultCustomTabsIntentBuilder(): CustomTabsIntent.Builder {
        val backArrow = getBitmapFromVectorDrawable(R.drawable.ic_arrow_back_black_24dp)
        return CustomTabsIntent.Builder()
            .enableUrlBarHiding()
            .addDefaultShareMenuItem()
            .setToolbarColor(this.resources.getColor(R.color.colorWhite))
            .setShowTitle(true)
            .setCloseButtonIcon(backArrow!!)
    }

    /**
     * Converts a vector asset to a bitmap as required by [CustomTabsIntent.Builder.setCloseButtonIcon]
     *
     * @param drawableId The drawable ID
     * @return Bitmap equivalent
     */
    private fun getBitmapFromVectorDrawable(@DrawableRes drawableId: Int): Bitmap? {
        var drawable: Drawable? = AppCompatResources.getDrawable(this, drawableId) ?: return null
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable!!).mutate()
        }

        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

}
