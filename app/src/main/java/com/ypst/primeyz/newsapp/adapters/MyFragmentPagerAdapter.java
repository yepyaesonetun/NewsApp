package com.ypst.primeyz.newsapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yepyaesonetun on 6/24/18.
 * called by one parameter constructor.
 * Generic Fragment Pager Adapter.
 * Fragments will be attached from outside that won't be Fragment Object duplications.
 **/

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mFragmentTitleList = new ArrayList<>();
    private int vpCount = mFragmentList.size();

    /**
     * @param manager : FragmentManager
     */
    public MyFragmentPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public void setVpCount(int count) {
        vpCount = count;
        notifyDataSetChanged();
    }

    public void setNewList(List<Fragment> newList) {
        mFragmentList = newList;
        notifyDataSetChanged();
    }
}



