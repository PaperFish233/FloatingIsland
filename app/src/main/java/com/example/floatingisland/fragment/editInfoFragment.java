package com.example.floatingisland.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.floatingisland.R;
import com.example.floatingisland.entity.Users;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.MyToast;

public class editInfoFragment extends Fragment {

    private View editInfoFragment;

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        editInfoFragment = inflater.inflate(R.layout.fragment_editinfo, container, false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        Toolbar toolbar = editInfoFragment.findViewById(R.id.Toolbar_editinfo);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        ImageView background = editInfoFragment.findViewById(R.id.background);
        SuperTextView avatar = editInfoFragment.findViewById(R.id.avatar);
        SuperTextView nickname = editInfoFragment.findViewById(R.id.nickname);
        SuperTextView account = editInfoFragment.findViewById(R.id.account);
        SuperTextView signature = editInfoFragment.findViewById(R.id.signature);
        ImageView update = editInfoFragment.findViewById(R.id.update);

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
                for (Users users : response.getData()) {
                    Uavatarurl = users.getUavatarurl();
                    Unickname = users.getUnickname();
                    Uaccount = users.getUaccount();
                    Usignature = users.getUsignature();
                    Ubackgroundurl = users.getUbackgroundurl();
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
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                avatar.setRightTvDrawableRight(resource);
                            }
                        });

                nickname.setRightString(Unickname);
                account.setRightString(Uaccount);
                signature.setRightString(Usignature);

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("lastuserInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("lastaccount", Uaccount);
                editor.putString("lastavatarurl", Uavatarurl);
                editor.apply();

            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.successBig("点击修改！");
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.successBig("点击头像！");

            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.successBig("点击账号！");

            }
        });

        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.successBig("点击昵称！");
            }
        });

        signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.successBig("点击简介！");
            }
        });

        return editInfoFragment;
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