package com.example.floatingisland.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.request.RequestOptions;
import com.example.floatingisland.R;
import com.example.floatingisland.entity.Posts;
import com.example.floatingisland.entity.Users;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.MyPostsAdapter;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;
import es.dmoral.toasty.MyToast;

public class userInfoFragment extends Fragment {

    private View userInfoFragment;
    private RecyclerView recyclerView;
    private MyPostsAdapter adapter;
    private ImageView background;
    private Button focus;
    private ImageView avatar;
    private TextView nickname;
    private TextView signature;
    private TextView account;
    private SuperTextView postsnum;
    private TextView ffocusnum;
    private TextView ufocusnum;
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
        getUserInfoAndPosts();
    }

    private void getUserInfoAndPosts(){

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userInfoPid", Context.MODE_PRIVATE);
        String pid = sharedPreferences.getString("pid", "");
        String faccount = sharedPreferences.getString("faccount", "");

        SharedPreferences sharedPreferences1 = getContext().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences1.getString("account", "");

        if(pid.isEmpty()){
            //关注状态检测
            HashMap<String, String> params1 = new HashMap<>();
            params1.put("faccount", faccount);
            params1.put("uaccount", loginInfo);
            OkHttp.post(getContext(), Constant.selectfocusbyuaccount, params1, new OkCallback<Result>() {
                @Override
                public void onResponse(Result response) {
                    if (response.getMessage().equals("已关注")) {
                        focus.setText("取关");
                        focus.setTextColor(Color.BLACK);
                        focus.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    } else {

                    }
                }

                @Override
                public void onFailure(String state, String msg) {
                    MyToast.errorBig("连接服务器超时！");
                }
            });

            //获取用户信息
            HashMap<String, String> params = new HashMap<>();
            params.put("uaccount", faccount);
            OkHttp.post(getContext(), Constant.getUserInfobyuaccount, params, new OkCallback<Result<List<Users>>>() {
                @Override
                public void onResponse(Result<List<Users>> response) {
                    String Uavatarurl="";
                    String Unickname="";
                    String Uaccount="";
                    String Usignature="";
                    String Ubackgroundurl="";
                    String Upostsnum="";
                    String Uffocusnum="";
                    String Uufocusnum="";
                    for (Users users : response.getData()) {
                        Uavatarurl = users.getUavatarurl();
                        Unickname = users.getUnickname();
                        Uaccount = users.getUaccount();
                        Usignature = users.getUsignature();
                        Ubackgroundurl = users.getUbackgroundurl();
                        Upostsnum = String.valueOf(users.getPostsnum());
                        Uffocusnum = String.valueOf(users.getFfocusnum());
                        Uufocusnum = String.valueOf(users.getUfocusnum());
                    }

                    Glide.with(getContext())
                            .load(Ubackgroundurl)
                            .apply(RequestOptions.overrideOf(1280, 720))
                            .into(background);

                    Glide.with(getContext())
                            .load(Uavatarurl)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(100))
                                    .override(100, 100))
                            .transform(new CircleTransformation())
                            .into(avatar);

                    nickname.setText(Unickname);
                    account.setText(Uaccount);
                    signature.setText(Usignature);
                    postsnum.setLeftString("用户发布（"+Upostsnum+"）");
                    ffocusnum.setText(Uffocusnum+"关注");
                    ufocusnum.setText(Uufocusnum+"粉丝");

                }

                @Override
                public void onFailure(String state, String msg) {
                    MyToast.errorBig("连接服务器超时！");
                }
            });

            //获取用户帖子
            HashMap<String, String> params2 = new HashMap<>();
            params2.put("uaccount", faccount);
            OkHttp.post(getContext(), Constant.getUserPostsbyuaccount, params2, new OkCallback<Result<List<Posts>>>() {
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
                    // 获取 RecyclerView 控件
                    recyclerView = userInfoFragment.findViewById(R.id.recycler_view);
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

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInfoPid", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear().apply();
                }

                @Override
                public void onFailure(String state, String msg) {
                    MyToast.errorBig("连接服务器超时！");
                }
            });

            //关注按钮点击事件
            focus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("faccount", faccount);
                    params.put("uaccount", loginInfo);
                    OkHttp.post(getContext(), Constant.focusbyuaccount, params, new OkCallback<Result>() {
                        @Override
                        public void onResponse(Result response) {
                            MyToast.successBig(response.getMessage());
                            if(response.getMessage().equals("关注成功")){
                                focus.setText("取关");
                                focus.setTextColor(Color.BLACK);
                                focus.setBackgroundColor(Color.parseColor("#DDDDDD"));
                            }else{
                                focus.setText("关注");
                                focus.setTextColor(Color.WHITE);
                                focus.setBackgroundColor(Color.parseColor("#FF6200EE"));
                            }
                        }

                        @Override
                        public void onFailure(String state, String msg) {
                            MyToast.errorBig("连接服务器超时！");
                        }
                    });
                }
            });

        }else{
            //关注状态检测
            HashMap<String, String> params1 = new HashMap<>();
            params1.put("pid", String.valueOf(pid));
            params1.put("uaccount", loginInfo);
            OkHttp.post(getContext(), Constant.selectfocus, params1, new OkCallback<Result>() {
                @Override
                public void onResponse(Result response) {
                    if (response.getMessage().equals("已关注")) {
                        focus.setText("取关");
                        focus.setTextColor(Color.BLACK);
                        focus.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    } else {

                    }
                }

                @Override
                public void onFailure(String state, String msg) {
                    MyToast.errorBig("连接服务器超时！");
                }
            });

            //获取用户信息
            HashMap<String, String> params = new HashMap<>();
            params.put("pid", pid);
            OkHttp.post(getContext(), Constant.getUserInfo, params, new OkCallback<Result<List<Users>>>() {
                @Override
                public void onResponse(Result<List<Users>> response) {
                    String Uavatarurl="";
                    String Unickname="";
                    String Uaccount="";
                    String Usignature="";
                    String Ubackgroundurl="";
                    String Upostsnum="";
                    String Uffocusnum="";
                    String Uufocusnum="";
                    for (Users users : response.getData()) {
                        Uavatarurl = users.getUavatarurl();
                        Unickname = users.getUnickname();
                        Uaccount = users.getUaccount();
                        Usignature = users.getUsignature();
                        Ubackgroundurl = users.getUbackgroundurl();
                        Upostsnum = String.valueOf(users.getPostsnum());
                        Uffocusnum = String.valueOf(users.getFfocusnum());
                        Uufocusnum = String.valueOf(users.getUfocusnum());
                    }

                    Glide.with(getContext())
                            .load(Ubackgroundurl)
                            .apply(RequestOptions.overrideOf(1280, 720))
                            .into(background);

                    Glide.with(getContext())
                            .load(Uavatarurl)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(100))
                                    .override(100, 100))
                            .transform(new CircleTransformation())
                            .into(avatar);

                    nickname.setText(Unickname);
                    account.setText(Uaccount);
                    signature.setText(Usignature);
                    postsnum.setLeftString("用户发布（"+Upostsnum+"）");
                    ffocusnum.setText(Uffocusnum+"关注");
                    ufocusnum.setText(Uufocusnum+"粉丝");

                }

                @Override
                public void onFailure(String state, String msg) {
                    MyToast.errorBig("连接服务器超时！");
                }
            });

            //获取用户帖子
            HashMap<String, String> params2 = new HashMap<>();
            params2.put("pid", pid);
            OkHttp.post(getContext(), Constant.getUserPosts, params2, new OkCallback<Result<List<Posts>>>() {
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
                    // 获取 RecyclerView 控件
                    recyclerView = userInfoFragment.findViewById(R.id.recycler_view);
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

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInfoPid", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear().apply();
                }

                @Override
                public void onFailure(String state, String msg) {
                    MyToast.errorBig("连接服务器超时！");
                }
            });

            //关注按钮点击事件
            focus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("pid", String.valueOf(pid));
                    params.put("uaccount", loginInfo);
                    OkHttp.post(getContext(), Constant.focus, params, new OkCallback<Result>() {
                        @Override
                        public void onResponse(Result response) {
                            MyToast.successBig(response.getMessage());
                            if(response.getMessage().equals("关注成功")){
                                focus.setText("取关");
                                focus.setTextColor(Color.BLACK);
                                focus.setBackgroundColor(Color.parseColor("#DDDDDD"));
                            }else{
                                focus.setText("关注");
                                focus.setTextColor(Color.WHITE);
                                focus.setBackgroundColor(Color.parseColor("#FF6200EE"));
                            }
                        }

                        @Override
                        public void onFailure(String state, String msg) {
                            MyToast.errorBig("连接服务器超时！");
                        }
                    });
                }
            });
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        userInfoFragment = inflater.inflate(R.layout.fragment_userinfo,container,false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        Toolbar toolbar = userInfoFragment.findViewById(R.id.Toolbar_userinfo);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

        background = userInfoFragment.findViewById(R.id.background);
        focus = userInfoFragment.findViewById(R.id.focus);
        avatar = userInfoFragment.findViewById(R.id.avatar);
        nickname = userInfoFragment.findViewById(R.id.nickname);
        signature = userInfoFragment.findViewById(R.id.signature);
        account = userInfoFragment.findViewById(R.id.account);
        postsnum = userInfoFragment.findViewById(R.id.postsnum);
        ffocusnum = userInfoFragment.findViewById(R.id.ffocusnum);
        ufocusnum = userInfoFragment.findViewById(R.id.ufocusnum);
        isempty = userInfoFragment.findViewById(R.id.isempty);

        return userInfoFragment;
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

    // 定义自定义的 CircleTransformation
    public class CircleTransformation extends BitmapTransformation {
        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            Bitmap bitmap = TransformationUtils.circleCrop(pool, toTransform, outWidth, outHeight);
            return bitmap;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            // do nothing
        }
    }
}