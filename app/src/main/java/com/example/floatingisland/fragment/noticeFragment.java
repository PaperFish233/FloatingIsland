package com.example.floatingisland.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.OneActivity;
import com.example.floatingisland.entity.Notice;
import com.example.floatingisland.entity.Users;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.MyToast;

public class noticeFragment extends Fragment {

    private View noticeFragment;
    private String naccount="";

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        noticeFragment = inflater.inflate(R.layout.fragment_notice, container, false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        Toolbar toolbar = noticeFragment.findViewById(R.id.Toolbar_notice);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

        //接收传递值
        Bundle bundle = getArguments();
        int nid = 0;
        if (bundle != null) {
            nid = bundle.getInt("nid");
        }

        ImageView image = noticeFragment.findViewById(R.id.image);
        SuperTextView userinfo = noticeFragment.findViewById(R.id.userinfo);
        TextView content = noticeFragment.findViewById(R.id.content);

        //获取信息
        HashMap<String, String> params = new HashMap<>();
        params.put("nid", String.valueOf(nid));
        OkHttp.post(getContext(), Constant.getNoticeInfo, params, new OkCallback<Result<List<Notice>>>() {
            @Override
            public void onResponse(Result<List<Notice>> response) {
                String ncontent="";
                String nimageurl="";
                String ndate="";
                String uavatarurl="";
                String unickname="";
                for (Notice notice : response.getData()) {
                    naccount = notice.getNaccount();
                    ncontent = notice.getNcontent();
                    nimageurl = notice.getNimageurl();
                    ndate = notice.getNdate();
                    uavatarurl = notice.getUavatarurl();
                    unickname = notice.getUnickname();

                }

                Glide.with(getContext())
                        .load(nimageurl)
                        .apply(RequestOptions.overrideOf(1280, 720))
                        .into(image);

                Glide.with(getContext())
                        .load(uavatarurl)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(100))
                                .override(100, 100))
                        .transform(new CircleTransformation())
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                userinfo.setLeftIcon(resource);
                            }
                        });

                content.setText(ncontent);
                userinfo.setLeftTopString(unickname);
                userinfo.setLeftBottomString(ndate);

            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });

        userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInfoPid", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("faccount", naccount);
                editor.apply();

                Intent intent = new Intent(getContext(), OneActivity.class);
                intent.putExtra("jumpcode", 3);
                startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
            }
        });

        return noticeFragment;
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