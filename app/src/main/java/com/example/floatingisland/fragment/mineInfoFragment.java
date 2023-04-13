package com.example.floatingisland.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.request.RequestOptions;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.ThereActivity;
import com.example.floatingisland.entity.Posts;
import com.example.floatingisland.entity.Users;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.MyMineAdapter;
import com.example.floatingisland.utils.MyPostsAdapter;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;
import es.dmoral.toasty.MyToast;

public class mineInfoFragment extends Fragment {

    private View mineInfoFragment;

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

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        ImageView background = mineInfoFragment.findViewById(R.id.background);
        ImageView avatar = mineInfoFragment.findViewById(R.id.avatar);
        TextView nickname = mineInfoFragment.findViewById(R.id.nickname);
        TextView signature = mineInfoFragment.findViewById(R.id.signature);
        TextView account = mineInfoFragment.findViewById(R.id.account);
        ImageView focus = mineInfoFragment.findViewById(R.id.focus);
        TextView postsnum = mineInfoFragment.findViewById(R.id.postsnum);
        TextView ffocusnum = mineInfoFragment.findViewById(R.id.ffocusnum);
        TextView ufocusnum = mineInfoFragment.findViewById(R.id.ufocusnum);

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
                postsnum.setText(Upostsnum+"发布");
                ffocusnum.setText(Uffocusnum+"关注");
                ufocusnum.setText(Uufocusnum+"粉丝");

            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });

        SmartTabLayout tabLayout = mineInfoFragment.findViewById(R.id.tab_layout);
        ViewPager viewPager = mineInfoFragment.findViewById(R.id.view_pager);

        MyMineAdapter adapter = new MyMineAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);

        focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return mineInfoFragment;
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