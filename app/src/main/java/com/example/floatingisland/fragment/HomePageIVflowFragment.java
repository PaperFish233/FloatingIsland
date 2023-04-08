package com.example.floatingisland.fragment;

import android.app.Application;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.floatingisland.R;
import com.example.floatingisland.activity.MainActivity;
import com.example.floatingisland.activity.OneActivity;
import com.example.floatingisland.entity.Posts;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.MyPostsAdapter;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import es.dmoral.toasty.MyToast;

public class HomePageIVflowFragment extends Fragment {

    private View HomePageIVflowFragment;
    private RecyclerView recyclerView;

    //将数据封装成数据源
    List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();

    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        HomePageIVflowFragment = inflater.inflate(R.layout.fragment_homepageivflow, container, false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        OkHttp.post(getContext(), Constant.getPosts, null, new OkCallback<Result<List<Posts>>>() {
            @Override
            public void onResponse(Result<List<Posts>> response) {
                for (Posts datum : response.getData()) {
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("pid",datum.getPid());
                    map.put("likenum",datum.getLikenum());
                    map.put("avatarurl",datum.getAvatarurl());
                    map.put("nickname",datum.getNickname());
                    map.put("datetime",datum.getDate());
                    map.put("content",datum.getContent());
                    map.put("imageurl",datum.getImageurl());
                    map.put("topicname",datum.getTopicname());
                    list.add(map);
                }
                // 获取 RecyclerView 控件
                recyclerView = HomePageIVflowFragment.findViewById(R.id.recycler_view);
                // 创建 LinearLayoutManager 对象，设置为垂直方向
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                // 绑定 LayoutManager
                recyclerView.setLayoutManager(layoutManager);

                // 绑定数据适配器MyAdapter
                MyPostsAdapter MyPostsAdapter = new MyPostsAdapter(getContext(),list,recyclerView);
                recyclerView.setAdapter(MyPostsAdapter);

                // 创建 SpaceItemDecoration 实例，设置5dp的间距
                SpaceItemDecoration decoration = new SpaceItemDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
                // 添加
                recyclerView.addItemDecoration(decoration);
            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });

        final RefreshLayout refreshLayout = HomePageIVflowFragment.findViewById(R.id.refreshLayout);
        //设置 Header 为 经典 样式
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();//传入false表示刷新失败
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
            }
        });

        //发布新贴按钮
        FloatingActionButton fab = HomePageIVflowFragment.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), OneActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
                }
        });

        return HomePageIVflowFragment;
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        // 设置第二个 item 和后续 item 之间的间距
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) > 0) {
                outRect.top = space;
            }
        }
    }

}