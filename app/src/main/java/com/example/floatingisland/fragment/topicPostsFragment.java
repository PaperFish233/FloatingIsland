package com.example.floatingisland.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.floatingisland.R;
import com.example.floatingisland.entity.Posts;
import com.example.floatingisland.entity.Topic;
import com.example.floatingisland.entity.Users;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.MyInfoPostsAdapter;
import com.example.floatingisland.utils.MyPostsAdapter;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;
import es.dmoral.toasty.MyToast;

public class topicPostsFragment extends Fragment {

    private View topicPostsFragment;
    private RecyclerView recyclerView;
    private MyPostsAdapter adapter;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        topicPostsFragment = inflater.inflate(R.layout.fragment_topicposts, container, false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        Toolbar toolbar = topicPostsFragment.findViewById(R.id.Toolbar_topicposts);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

        ImageView background = topicPostsFragment.findViewById(R.id.background);
        ImageView tbg = topicPostsFragment.findViewById(R.id.tbg);
        TextView tname = topicPostsFragment.findViewById(R.id.tname);
        TextView tsignature = topicPostsFragment.findViewById(R.id.tsignature);
        SuperTextView postsnum = topicPostsFragment.findViewById(R.id.postsnum);
        ImageView isempty = topicPostsFragment.findViewById(R.id.isempty);


        //接收传递值
        Bundle bundle = getArguments();
        String tid = "";
        if (bundle != null) {
            tid = bundle.getString("tid");
        }

        //获取进入话题信息
        HashMap<String, String> params = new HashMap<>();
        params.put("tid",tid);
        OkHttp.post(getContext(), Constant.getTidTopic, params, new OkCallback<Result<List<Topic>>>() {
            @Override
            public void onResponse(Result<List<Topic>> response) {
                String Tbackgroundurl="";
                String Tname="";
                String Tsignature="";
                int Tpostsnum=0;
                for (Topic topic : response.getData()) {
                    Tbackgroundurl = topic.getTimageurl();
                    Tname = topic.getTname();
                    Tsignature = topic.getTsignature();
                    Tpostsnum = topic.getTpostsnum();
                }

                Glide.with(getContext())
                        .load(Tbackgroundurl)
                        .apply(RequestOptions.overrideOf(1280, 720))
                        .into(background);

                int cornerRadius = 20;
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.transforms(new CenterCrop(), new RoundedCorners(cornerRadius));
                requestOptions.override(128, 128);
                Glide.with(getContext())
                        .load(Tbackgroundurl)
                        .apply(requestOptions)
                        .into(tbg);

                tname.setText(Tname);
                tsignature.setText(Tsignature);

                postsnum.setLeftString("话题帖子（"+Tpostsnum+"）");


            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });


        //加载话题下帖子
        HashMap<String, String> params1 = new HashMap<>();
        params1.put("tid",tid);
        OkHttp.post(getContext(), Constant.getTidPosts, params1, new OkCallback<Result<List<Posts>>>() {
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
                    map.put("topicid",datum.getTopicid());
                    map.put("topicname",datum.getTopicname());
                    map.put("topicimageurl",datum.getTopicimageurl());
                    list.add(map);

                }
                // 获取 RecyclerView 控件
                recyclerView = topicPostsFragment.findViewById(R.id.recycler_view);
                // 创建 LinearLayoutManager 对象，设置为垂直方向
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                // 绑定 LayoutManager
                recyclerView.setLayoutManager(layoutManager);

                //无发布页面显示标语
                if(list.isEmpty()){
                    isempty.setVisibility(View.VISIBLE);
                }else{
                    isempty.setVisibility(View.GONE);
                    // 绑定数据适配器MyAdapter
                    MyPostsAdapter MyPostsAdapter = new MyPostsAdapter(getContext(),list,recyclerView,getActivity());
                    recyclerView.setAdapter(MyPostsAdapter);
                }

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


        return topicPostsFragment;
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