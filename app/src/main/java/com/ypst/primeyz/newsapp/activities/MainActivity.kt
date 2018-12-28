package com.ypst.primeyz.newsapp.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.DrawableRes
import android.support.customtabs.CustomTabsIntent
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.content.res.AppCompatResources
import android.util.Log
import android.widget.Toast
import com.ypst.primeyz.newsapp.R
import com.ypst.primeyz.newsapp.adapters.MyFragmentPagerAdapter
import com.ypst.primeyz.newsapp.data.vos.NewsVO
import com.ypst.primeyz.newsapp.data.vos.SourceVO
import com.ypst.primeyz.newsapp.delegates.HomePresenterDelegate
import com.ypst.primeyz.newsapp.fragments.HeadLinesFragment
import com.ypst.primeyz.newsapp.fragments.HomeFragment
import com.ypst.primeyz.newsapp.fragments.NewsStandFragment
import com.ypst.primeyz.newsapp.mvp.presenters.MainPresenter
import com.ypst.primeyz.newsapp.mvp.views.MainView
import com.ypst.primeyz.newsapp.utils.AppConstants
import kotlinx.android.synthetic.main.activity_main.*
import saschpe.android.customtabs.CustomTabsHelper
import saschpe.android.customtabs.WebViewFallback


class MainActivity : AppCompatActivity(), MainView, HomePresenterDelegate {
    override fun navigateToAllSourceListActivity() {
        startActivity(SourceListActivity.getIntent(this))
    }

    override fun navigateToNewsListBySourceID(sourceID: String, sourceName: String) {
        startActivity(HeadLinesBySourceActivity.getIntent(this, sourceID, sourceName))
    }

    override fun displayPopularSourceList(dataList: List<SourceVO>) {
        val newsStandFragment: NewsStandFragment = mMainFragmentPagerAdapter!!.getItem(2) as NewsStandFragment
        newsStandFragment.addDataToAdapter(dataList)
    }

    override fun displayTodayDate(dateString: String) {
        val homeFragment: HomeFragment = mMainFragmentPagerAdapter!!.getItem(0) as HomeFragment
        homeFragment.displayTodayDate(dateString)
    }

    override fun displayHeadLineNewsDataList(dataList: List<NewsVO>) {
        val homeFragment: HomeFragment = mMainFragmentPagerAdapter!!.getItem(0) as HomeFragment
        homeFragment.addDataToAdapter(dataList)
    }

    override fun displayHeadLineNewsDatListByCategory(dataList: List<NewsVO>) {
        val headlineFrament: HeadLinesFragment = mMainFragmentPagerAdapter!!.getItem(1) as HeadLinesFragment
        headlineFrament.addDataToAdapter(dataList)
    }


    private var mDoubleBackToExitPressedOnce = false
    private var mMainFragmentPagerAdapter: MyFragmentPagerAdapter? = null
    private lateinit var mPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()

        mPresenter = ViewModelProviders.of(this).get(MainPresenter::class.java)
        mPresenter.initPresenter(this)
        mPresenter.onLoadTodayDate()

        mPresenter.onLoadHeadLineNewsData("us", 1)
        observeForYouNewsDataList()

        mPresenter.onLoadHeadLineDataListWithTappedCategoryName("us", AppConstants.CATEGORY_BUSSINESS, 1)
        observeHeadLineDataListByCategoryName()

        mPresenter.onLoadSourceList()
        observeSourceList()

        mPresenter.errorLD.observe(this, Observer<String> { errorMsg ->
            Log.e("count", "$errorMsg")
        })
    }

    private fun observeSourceList() {
        mPresenter.popularSourceListLiveData.observe(this,
            Observer<List<SourceVO>> { sourceList: List<SourceVO>? ->
                displayPopularSourceList(sourceList!!)
            })
    }

    private fun observeHeadLineDataListByCategoryName() {
        mPresenter.headLineNewsVOListByCategoryLiveData.observe(this,
            Observer<List<NewsVO>> { newsVOList: List<NewsVO>? ->
                displayHeadLineNewsDatListByCategory(newsVOList!!)
            })
    }

    private fun observeForYouNewsDataList() {
        mPresenter.headLineNewsVOListLiveData.observe(this,
            Observer<List<NewsVO>> { newsVOList: List<NewsVO>? ->
                displayHeadLineNewsDataList(newsVOList!!)
            })
    }

    override fun navigateToSearchActivity() {
        startActivity(SearchActivity.getIntent(context = this))
    }

    /**
     * helper method for communication between Host Activity and Child Fragments
     */
    override fun getPresenter(): MainPresenter {
        return mPresenter
    }

    /**
     * initial views setup
     * bottomNavigation and ViewPager completed
     */
    private fun bindViews() {
        // Set a navigation item selected listener for bottom navigation view
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    view_pager.currentItem = 0
                    true
                }
                R.id.navigation_head_line -> {
                    view_pager.currentItem = 1
                    true
                }
                R.id.navigation_news_stand -> {
                    view_pager.currentItem = 2
                    true
                }
                else -> false
            }
        }

        mMainFragmentPagerAdapter = MyFragmentPagerAdapter(supportFragmentManager)
        mMainFragmentPagerAdapter!!.run {
            addFragment(HomeFragment(), getString(R.string.title_home))
            addFragment(HeadLinesFragment(), getString(R.string.title_head_line))
            addFragment(NewsStandFragment(), getString(R.string.title_news_stand))
        }

        view_pager.offscreenPageLimit = 3
        view_pager.adapter = mMainFragmentPagerAdapter

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        navigation.menu.getItem(0).isChecked = true
                    }

                    1 -> {
                        navigation.menu.getItem(1).isChecked = true
                    }
                    2 -> {
                        navigation.menu.getItem(2).isChecked = true
                    }
                }

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


    }


    /**
     * double press to exit and move to home fragment when back pressed from other fragments
     */
    override fun onBackPressed() {
        val selectedItemId = navigation.selectedItemId
        @Suppress("DEPRECATED_IDENTITY_EQUALS")
        when {
            R.id.navigation_home !== selectedItemId -> navigation.selectedItemId = R.id.navigation_home
            else -> {
                when {
                    mDoubleBackToExitPressedOnce -> {
                        super.onBackPressed()
                        return
                    }
                    else -> {
                        this.mDoubleBackToExitPressedOnce = true
                        Toast.makeText(this, getString(R.string.lbl_double_back_to_exit), Toast.LENGTH_SHORT).show()

                        Handler().postDelayed({ mDoubleBackToExitPressedOnce = false }, 2000)
                    }
                }
            }
        }
    }

    /**
     * @param categoryNameString
     * when selected category Name from HeadLine Fragment,
     * load data with that selected name as api parameter
     */
    override fun accessOnTappedCategoryName(categoryNameString: String) {
        mPresenter.onLoadHeadLineDataListWithTappedCategoryName("us", categoryNameString, 1)
    }


    /**
     * @param urlString is Full url to Navigate
     */
    override fun navigateToNewsSourceUrl(urlString: String) {
        startLoadUrl(urlString)
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
