package com.example.floatingisland.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.request.RequestOptions;
import com.example.floatingisland.R;
import com.example.floatingisland.entity.Posts;
import com.example.floatingisland.entity.Users;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.MyInfoPostsAdapter;
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

public class mineInfoFragment extends Fragment {

    private View mineInfoFragment;
    private RecyclerView recyclerView;
    private MyPostsAdapter adapter;
    private ImageView background;
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

    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        getUserAndPosts();
    }

    private void getUserAndPosts(){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        //获取登录用户信息
        HashMap<String, String> params = new HashMap<>();
        params.put("uaccount", loginInfo);
        OkHttp.post(getContext(), Constant.getUsers, params, new OkCallback<Result<List<Users>>>() {
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
                postsnum.setLeftString("我的发布（"+Upostsnum+"）");
                ffocusnum.setText(Uffocusnum+"关注");
                ufocusnum.setText(Uufocusnum+"粉丝");

            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });

        //加载我的帖子
        HashMap<String, String> params1 = new HashMap<>();
        params1.put("uaccount", loginInfo);
        OkHttp.post(getContext(), Constant.getminePosts, params1, new OkCallback<Result<List<Posts>>>() {
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

                //无发布页面显示标语
                if(list.isEmpty()){
                    isempty.setVisibility(View.VISIBLE);
                }else{
                    isempty.setVisibility(View.GONE);
                    // 绑定数据适配器MyAdapter
                    MyInfoPostsAdapter MyInfoPostsAdapter = new MyInfoPostsAdapter(getContext(),list,recyclerView,getActivity());
                    recyclerView.setAdapter(MyInfoPostsAdapter);
                }

            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });
    }

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mineInfoFragment = inflater.inflate(R.layout.fragment_mineinfo,container,false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        Toolbar toolbar = mineInfoFragment.findViewById(R.id.Toolbar_mineinfo);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()
            }
        });

        // 获取 RecyclerView 控件
        recyclerView = mineInfoFragment.findViewById(R.id.recycler_view);

        background = mineInfoFragment.findViewById(R.id.background);
        avatar = mineInfoFragment.findViewById(R.id.avatar);
        nickname = mineInfoFragment.findViewById(R.id.nickname);
        signature = mineInfoFragment.findViewById(R.id.signature);
        account = mineInfoFragment.findViewById(R.id.account);
        postsnum = mineInfoFragment.findViewById(R.id.postsnum);
        ffocusnum = mineInfoFragment.findViewById(R.id.ffocusnum);
        ufocusnum = mineInfoFragment.findViewById(R.id.ufocusnum);
        isempty = mineInfoFragment.findViewById(R.id.isempty);

        // 创建 SpaceItemDecoration 实例，设置5dp的间距
        SpaceItemDecoration decoration = new SpaceItemDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        // 添加
        recyclerView.addItemDecoration(decoration);

        return mineInfoFragment;
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