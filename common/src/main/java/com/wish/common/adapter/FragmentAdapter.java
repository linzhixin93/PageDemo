package com.wish.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Copyright (c) 2019, 四川绿源集科技有限公司 All rights reserved.
 * author：LinZhiXin
 * date：2019/3/25
 * description：Fragment适配器，可以加标题
 **/
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private String[] titleArray;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList,
                           String ... titleArray) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleArray = titleArray;
    }

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList == null ? null : fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArray == null ? super.getPageTitle(position) : titleArray[position];
    }
}
