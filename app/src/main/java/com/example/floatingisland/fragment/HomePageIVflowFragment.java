package com.example.floatingisland.fragment;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.OneActivity;
import com.example.floatingisland.activity.ThereActivity;
import com.example.floatingisland.entity.Notice;
import com.example.floatingisland.entity.Posts;
import com.example.floatingisland.entity.Users;
import com.example.floatingisland.utils.Constant;
//import com.example.floatingisland.utils.GlideImageLoader;
import com.example.floatingisland.utils.MyPostsAdapter;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.listener.OnBannerListener;
//import com.youth.banner.Transformer;
//import com.youth.banner.listener.OnBannerClickListener;

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
    private Intent noticeintent;
    private Banner banner;

    //将数据封装成数据源
    List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
    //获取轮播图地址
    List<String> imageurlsList = new ArrayList<>();
    //获取nid
    List<Integer> nidList = new ArrayList<>();

    //获取帖子数据
    private void getPostsDate() {

        OkHttp.post(getContext(), Constant.getPosts, null, new OkCallback<Result<List<Posts>>>() {
            @Override
            public void onResponse(Result<List<Posts>> response) {
                for (Posts datum : response.getData()) {
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("pid",datum.getPid());
                    map.put("likenum",datum.getLikenum());
                    map.put("collectionnum",datum.getCollectionnum());
                    map.put("commentnum",datum.getCommentnum());
                    map.put("avatarurl",datum.getAvatarurl());
                    map.put("nickname",datum.getNickname());
                    map.put("datetime",datum.getDate());
                    map.put("content",datum.getContent());
                    map.put("imageurl",datum.getImageurl());
                    map.put("topicid",datum.getTopicid());
                    map.put("topicname",datum.getTopicname());
                    map.put("topicimageurl",datum.getTopicimageurl());
                    list.add(map);
                }
                // 绑定数据适配器MyAdapter
                MyPostsAdapter MyPostsAdapter = new MyPostsAdapter(getContext(),list,recyclerView,getActivity());
                recyclerView.setAdapter(MyPostsAdapter);
            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });

        //获取轮播图信息
        OkHttp.post(getContext(), Constant.getNotice, null, new OkCallback<Result<List<Notice>>>() {
            @Override
            public void onResponse(Result<List<Notice>> response) {
                for (Notice notice : response.getData()) {
                    int nid = notice.getNid();
                    nidList.add(nid);
                    String imageurl = notice.getNimageurl();
                    imageurlsList.add(imageurl);
                }
                banner.setAdapter(new BannerImageAdapter<String>(imageurlsList) {
                            @Override
                            public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                                Glide.with(holder.itemView)
                                        .load(data)
                                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                        .into(holder.imageView);
                            }})
                        .setIndicator(new CircleIndicator(getContext()))
                        .setIndicatorSelectedColor(Color.WHITE)
                        .setBannerGalleryEffect(20,20,0)
                        .addBannerLifecycleObserver(getActivity())
                        .setOnBannerListener(
                                (OnBannerListener<String>)(data, position) -> handleBannerClick(position));

            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    //通过 ViewPager 进行滑动切换时释放资源
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            Jzvd.releaseAllVideos(); // 释放资源
        }
    }

    private void handleBannerClick(int position) {
        switch (position) {
            case 0:
                int nid = nidList.get(0);
                noticeintent.putExtra("jumpcode", 4);
                noticeintent.putExtra("nid", nid);
                startActivity(noticeintent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
                break;
            case 1:
                int nid1 = nidList.get(1);
                noticeintent.putExtra("jumpcode", 4);
                noticeintent.putExtra("nid", nid1);
                startActivity(noticeintent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
                break;
            case 2:
                int nid2 = nidList.get(2);
                noticeintent.putExtra("jumpcode", 4);
                noticeintent.putExtra("nid", nid2);
                startActivity(noticeintent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
                break;
            case 3:
                int nid3 = nidList.get(3);
                noticeintent.putExtra("jumpcode", 4);
                noticeintent.putExtra("nid", nid3);
                startActivity(noticeintent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
                break;
            case 4:
                int nid4 = nidList.get(4);
                noticeintent.putExtra("jumpcode", 4);
                noticeintent.putExtra("nid", nid4);
                startActivity(noticeintent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        HomePageIVflowFragment = inflater.inflate(R.layout.fragment_homepageivflow, container, false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        noticeintent = new Intent(getContext(), OneActivity.class);

        // 获取控件
        recyclerView = HomePageIVflowFragment.findViewById(R.id.recycler_view);
        banner = HomePageIVflowFragment.findViewById(R.id.banner);

        OkHttp.post(getContext(), Constant.getPosts, null, new OkCallback<Result<List<Posts>>>() {
            @Override
            public void onResponse(Result<List<Posts>> response) {
                for (Posts datum : response.getData()) {
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("pid",datum.getPid());
                    map.put("likenum",datum.getLikenum());
                    map.put("collectionnum",datum.getCollectionnum());
                    map.put("commentnum",datum.getCommentnum());
                    map.put("avatarurl",datum.getAvatarurl());
                    map.put("nickname",datum.getNickname());
                    map.put("datetime",datum.getDate());
                    map.put("content",datum.getContent());
                    map.put("imageurl",datum.getImageurl());
                    map.put("topicid",datum.getTopicid());
                    map.put("topicname",datum.getTopicname());
                    map.put("topicimageurl",datum.getTopicimageurl());
                    list.add(map);
                }

                // 创建 LinearLayoutManager 对象，设置为垂直方向
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                // 绑定 LayoutManager
                recyclerView.setLayoutManager(layoutManager);

                // 绑定数据适配器MyAdapter
                MyPostsAdapter MyPostsAdapter = new MyPostsAdapter(getContext(),list,recyclerView,getActivity());
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



        //获取轮播图信息
        OkHttp.post(getContext(), Constant.getNotice, null, new OkCallback<Result<List<Notice>>>() {
            @Override
            public void onResponse(Result<List<Notice>> response) {
                for (Notice notice : response.getData()) {
                    int nid = notice.getNid();
                    nidList.add(nid);
                    String imageurl = notice.getNimageurl();
                    imageurlsList.add(imageurl);
                }
                    banner.setAdapter(new BannerImageAdapter<String>(imageurlsList) {
                                @Override
                                public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                                    Glide.with(holder.itemView)
                                            .load(data)
                                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                            .into(holder.imageView);
                                }})
                            .setIndicator(new CircleIndicator(getContext()))
                            .setIndicatorSelectedColor(Color.WHITE)
                            .setBannerGalleryEffect(20,20,0)
                            .addBannerLifecycleObserver(getActivity())
                            .setOnBannerListener(
                                    (OnBannerListener<String>)(data, position) -> handleBannerClick(position));

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
                list.clear();
                imageurlsList.clear();
                nidList.clear();
                getPostsDate();
                banner.setDatas(imageurlsList);
            }
        });

        //发布新贴按钮
        FloatingActionButton fab = HomePageIVflowFragment.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), OneActivity.class);
                intent.putExtra("jumpcode",1);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
                }
        });

        NestedScrollView nv1 = HomePageIVflowFragment.findViewById(R.id.nv1);
        nv1.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // 向下滑动时隐藏 FloatingActionButton
                if (scrollY > oldScrollY && fab.isShown()) {
                    fab.hide();
                // 向上滑动时显示 FloatingActionButton
                } else if (scrollY < oldScrollY && !fab.isShown()) {
                    fab.show();
                }
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