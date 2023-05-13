package com.example.floatingisland.fragment;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.floatingisland.R;
import com.example.floatingisland.entity.Posts;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.MyCommentAdapter;
import com.example.floatingisland.utils.MyPostsAdapter;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;
import es.dmoral.toasty.MyToast;


public class mineCollectionPostsFragment extends Fragment {

    private View mineCollectionPostsFragment;
    private RecyclerView recyclerView;
    private MyPostsAdapter adapter;
    private ImageView isempty;

    //将数据封装成数据源
    List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        getmineCollectionPosts();
    }

    private void getmineCollectionPosts(){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        HashMap<String, String> params = new HashMap<>();
        params.put("uaccount", loginInfo);
        OkHttp.post(getContext(), Constant.getmineCollectionPosts, params, new OkCallback<Result<List<Posts>>>() {
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

                //无收藏页面显示标语
                if(list.isEmpty()){
                    isempty.setVisibility(View.VISIBLE);
                }else{
                    isempty.setVisibility(View.GONE);
                    // 绑定数据适配器MyAdapter
                    MyPostsAdapter MyPostsAdapter = new MyPostsAdapter(getContext(),list,recyclerView,getActivity());
                    recyclerView.setAdapter(MyPostsAdapter);
                }
            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mineCollectionPostsFragment = inflater.inflate(R.layout.fragment_minecollectionposts, container, false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        // 获取 RecyclerView 控件
        recyclerView = mineCollectionPostsFragment.findViewById(R.id.recycler_view);
        isempty = mineCollectionPostsFragment.findViewById(R.id.isempty);

        Toolbar toolbar = mineCollectionPostsFragment.findViewById(R.id.Toolbar_minecollectionposts);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

        // 创建 SpaceItemDecoration 实例，设置5dp的间距
        SpaceItemDecoration decoration = new SpaceItemDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        // 添加
        recyclerView.addItemDecoration(decoration);

        return mineCollectionPostsFragment;
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