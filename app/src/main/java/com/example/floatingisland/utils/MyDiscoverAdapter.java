package com.example.floatingisland.utils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.floatingisland.fragment.DiscoverRKFragment;
import com.example.floatingisland.fragment.DiscoverTPFragment;
import com.example.floatingisland.fragment.HomePageFPflowFragment;
import com.example.floatingisland.fragment.HomePageIVflowFragment;

import java.util.ArrayList;
import java.util.List;

public class MyDiscoverAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public MyDiscoverAdapter(FragmentManager fm) {
        super(fm);
        fragmentList.add(new DiscoverTPFragment());
        fragmentList.add(new DiscoverRKFragment());

        titleList.add("话题圈");
        titleList.add("排行榜");

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
