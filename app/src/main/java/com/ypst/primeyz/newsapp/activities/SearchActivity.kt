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
import android.support.v7.app.AppCompatActivity
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import com.ypst.primeyz.newsapp.R
import com.ypst.primeyz.newsapp.adapters.SearchResultsRVAdapter
import com.ypst.primeyz.newsapp.components.SmartRecyclerView
import com.ypst.primeyz.newsapp.data.vos.NewsVO
import com.ypst.primeyz.newsapp.mvp.presenters.SearchPresenter
import com.ypst.primeyz.newsapp.mvp.views.EverythingSearchView
import kotlinx.android.synthetic.main.activity_search.*
import saschpe.android.customtabs.CustomTabsHelper
import saschpe.android.customtabs.WebViewFallback


class SearchActivity : AppCompatActivity(), EverythingSearchView {
    override fun navigateToNewsSourceUrl(urlString: String) {
        startLoadUrl(urlString)
    }

    override fun displayResultList(resultlist: List<NewsVO>) {
        mAdapter.clearData()
        mAdapter.appendNewData(resultlist)
    }

    override fun finishActivity() {
        finish()
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    private lateinit var mPresenter: SearchPresenter
    private lateinit var mAdapter : SearchResultsRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mPresenter = ViewModelProviders.of(this).get(SearchPresenter::class.java)
        mPresenter.initPresenter(this)

        mAdapter = SearchResultsRVAdapter(this, mPresenter)

        val dividerDrawable = ContextCompat.getDrawable(this, R.drawable.line_divider)
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(dividerDrawable!!)

        rvResults.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rvResults.setHasFixedSize(true)
        rvResults.isNestedScrollingEnabled = false
        rvResults.addItemDecoration(decoration)
        rvResults.adapter = mAdapter

        edtSearchKey.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mPresenter.onActionSearch(edtSearchKey.text.toString(), "en")
                tvTypedKeyWord.text = edtSearchKey.text.toString()
                return@OnEditorActionListener true
            }
            false
        })


        edtSearchKey.setOnTouchListener(View.OnTouchListener { _, event ->
            val DRAWABLE_RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= edtSearchKey.right - edtSearchKey.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    edtSearchKey.setText("")
                    return@OnTouchListener true
                }
            }
            false
        })

        observeResultDatalist()

        mPresenter.errorLD.observe(this, Observer<String> { errorMsg ->
            Log.e("error", "$errorMsg")
        })
    }

    override fun onStart() {
        super.onStart()
        ivBack.setOnClickListener { mPresenter.onTapBack() }
    }

    private fun observeResultDatalist() {
        mPresenter.resultListLiveData.observe(this,
            Observer<List<NewsVO>> { newsVoList: List<NewsVO>? ->
                displayResultList(newsVoList!!)
            })
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
