package com.example.floatingisland.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.MainActivity;
import com.example.floatingisland.activity.OneActivity;
import com.example.floatingisland.activity.ThereActivity;
import com.example.floatingisland.activity.WelcomeActivity;
import com.example.floatingisland.entity.Users;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.MyPagerAdapter;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.MyToast;

public class MineFragment extends Fragment {

    private View MineFragment;

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        MineFragment = inflater.inflate(R.layout.fragment_mine,container,false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");



        SuperTextView avatar = MineFragment.findViewById(R.id.avatar);
        SuperTextView nickname = MineFragment.findViewById(R.id.nickname);
        SuperTextView account = MineFragment.findViewById(R.id.account);
        SuperTextView signature = MineFragment.findViewById(R.id.signature);
        SuperTextView mineposts = MineFragment.findViewById(R.id.mineposts);
        SuperTextView minecollection = MineFragment.findViewById(R.id.minecollection);
        SuperTextView minecollectionpeople = MineFragment.findViewById(R.id.minecollectionpeople);
        SuperTextView exit = MineFragment.findViewById(R.id.exit);

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
                for (Users users : response.getData()) {
                    Uavatarurl = users.getUavatarurl();
                    Unickname = users.getUnickname();
                    Uaccount = users.getUaccount();
                    Usignature = users.getUsignature();
                }

                Glide.with(getContext())
                        .load(Uavatarurl)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(100))
                        .override(100, 100))
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                avatar.setRightIcon(resource);
                            }
                        });

                nickname.setRightString(Unickname);
                account.setRightString(Uaccount);
                signature.setRightString(Usignature);

            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
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

        mineposts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThereActivity.class);
                intent.putExtra("jumpcode",1);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
            }
        });

        minecollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThereActivity.class);
                intent.putExtra("jumpcode",2);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
            }
        });

        minecollectionpeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.successBig("点击关注的人！");
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("确认退出改账号吗？"); // 消息正文

                // 设置确认按钮
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        Intent intent = new Intent(getContext(), WelcomeActivity.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        activity.onBackPressed();//销毁自己
                    }
                });

                // 设置取消按钮
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击取消后执行的操作
                        dialog.cancel(); // 关闭对话框
                    }
                });

                // 创建并显示对话框
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        return MineFragment;
    }

}