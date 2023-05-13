package com.example.floatingisland.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.floatingisland.R;
import com.example.floatingisland.activity.OneActivity;
import com.example.floatingisland.utils.MySearchAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import cn.jzvd.JzvdStd;

public class searchFragment extends Fragment {

    private View searchFragment;

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        searchFragment = inflater.inflate(R.layout.fragment_search,container,false);

        Toolbar toolbar = searchFragment.findViewById(R.id.Toolbar_search);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("searchInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()
            }
        });


        SmartTabLayout tabLayout = searchFragment.findViewById(R.id.tab_layout);
        ViewPager viewPager = searchFragment.findViewById(R.id.view_pager);

        MySearchAdapter adapter = new MySearchAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);

        //搜索按钮点击事件
        ImageView search = searchFragment.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                com.wyt.searchbox.SearchFragment searchFragment = com.wyt.searchbox.SearchFragment.newInstance();
                searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
                    @Override
                    public void OnSearchClick(String keyword) {
                        //这里处理逻辑
                        SharedPreferences sharedPreferences = activity.getSharedPreferences("searchInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("keyword", keyword);
                        editor.apply();

                        getFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_left)
                                .replace(R.id.oneLayout, new searchFragment())
                                .commit();
                    }
                });
                searchFragment.showFragment(getFragmentManager(), com.wyt.searchbox.SearchFragment.TAG);
            }
        });


        return searchFragment;
    }
}