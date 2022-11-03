package com.IN6222.myapplication.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment>fragmentList;

    public PagerAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        fragmentList=list;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
