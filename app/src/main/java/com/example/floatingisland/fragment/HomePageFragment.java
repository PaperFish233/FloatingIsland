package com.example.floatingisland.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.floatingisland.R;
import com.example.floatingisland.activity.OneActivity;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jem.fliptabs.FlipTab;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.JzvdStd;
import es.dmoral.toasty.MyToast;

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

    //通过 ViewPager 进行滑动切换时暂停播放器
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            JzvdStd.goOnPlayOnResume(); // 恢复播放
        } else {
            JzvdStd.goOnPlayOnPause(); // 暂停播放
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        HomePageFragment = inflater.inflate(R.layout.fragment_homepage,container,false);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.imagevideoLayout, new HomePageIVflowFragment())
                .commit();

        //tab选择卡
        FlipTab fliptab = HomePageFragment.findViewById(R.id.ft1);
        fliptab.setTabSelectedListener(new FlipTab.TabSelectedListener() {
            @Override
            public void onTabSelected(boolean isLeftTab, String tabTextValue) {
                if(isLeftTab==true){
                    getChildFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_from_right)
                            .replace(R.id.imagevideoLayout, new HomePageIVflowFragment())
                            .commit();
                }else{
                    getChildFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_left)
                            .replace(R.id.imagevideoLayout, new HomePageFPflowFragment())
                            .commit();
                }
            }

            @Override
            public void onTabReselected(boolean isLeftTab, String tabTextValue) {

            }
        });

        //点赞按钮点击事件
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

        //发布新贴按钮
        FloatingActionButton fab = HomePageFragment.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), OneActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
                }
        });

        return HomePageFragment;
    }



}