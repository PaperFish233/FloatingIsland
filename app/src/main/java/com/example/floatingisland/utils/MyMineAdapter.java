package com.example.floatingisland.utils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.floatingisland.fragment.HomePageFPflowFragment;
import com.example.floatingisland.fragment.HomePageIVflowFragment;
import com.example.floatingisland.fragment.mineCollectionPostsFragment;
import com.example.floatingisland.fragment.minePostsFragment;

import java.util.ArrayList;
import java.util.List;

public class MyMineAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public MyMineAdapter(FragmentManager fm) {
        super(fm);
        fragmentList.add(new minePostsFragment());

        titleList.add("我的帖子");
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
