package com.example.floatingisland.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.floatingisland.R;
import com.example.floatingisland.utils.MyPagerAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomePageFragment extends Fragment {

    private View HomePageFragment;

    //将数据封装成数据源
    List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        HomePageFragment = inflater.inflate(R.layout.fragment_homepage,container,false);

        SmartTabLayout tabLayout = HomePageFragment.findViewById(R.id.tab_layout);
        ViewPager viewPager = HomePageFragment.findViewById(R.id.view_pager);

        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);

        //搜索按钮点击事件
        ImageView search = HomePageFragment.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SearchFragment searchFragment = SearchFragment.newInstance();
                searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
                    @Override
                    public void OnSearchClick(String keyword) {
                        //这里处理逻辑

                    }
                });
                searchFragment.showFragment(getFragmentManager(),SearchFragment.TAG);
            }
        });

        return HomePageFragment;
    }

}
