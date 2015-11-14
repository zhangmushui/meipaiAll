package com.gzw.mp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gzw.mp.fragments.HomeTabFragment;

import java.util.List;

/**
 *
 * @author Grrsun
 *
 */
public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<String> list;

    public HomeFragmentPagerAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return HomeTabFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}

