package com.example.floatingisland.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.floatingisland.activity.MainActivity;
import com.example.floatingisland.activity.ThereActivity;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.OneActivity;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.example.floatingisland.entity.Posts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jem.fliptabs.FlipTab;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        HomePageFragment = inflater.inflate(R.layout.fragment_homepage,container,false);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.imagevideoLayout, new HomePageImageFragment())
                .commit();

        //tab选择卡
        FlipTab fliptab = HomePageFragment.findViewById(R.id.ft1);
        fliptab.setTabSelectedListener(new FlipTab.TabSelectedListener() {
            @Override
            public void onTabSelected(boolean isLeftTab, String tabTextValue) {
                if(isLeftTab==true){
                    getChildFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_from_right)
                            .replace(R.id.imagevideoLayout, new HomePageImageFragment())
                            .commit();
                }else{
                    getChildFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_left)
                            .replace(R.id.imagevideoLayout, new HomePageVideoFragment())
                            .commit();
                }
            }

            @Override
            public void onTabReselected(boolean isLeftTab, String tabTextValue) {

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