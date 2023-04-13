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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.WelcomeActivity;
import com.example.floatingisland.entity.Users;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;

import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.MyToast;

public class settingFragment extends Fragment {

    private View settingFragment;

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        settingFragment = inflater.inflate(R.layout.fragment_setting, container, false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        SuperTextView editinfo = settingFragment.findViewById(R.id.editinfo);
        SuperTextView agreement = settingFragment.findViewById(R.id.agreement);
        SuperTextView about = settingFragment.findViewById(R.id.about);
        SuperTextView exit = settingFragment.findViewById(R.id.exit);

        Toolbar toolbar = settingFragment.findViewById(R.id.Toolbar_setting);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

        editinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_left, R.anim.slide_in_from_left, R.anim.slide_out_from_right)
                        .replace(R.id.thereLayout, new editInfoFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.successBig("点击用户协议！");
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.successBig("点击关于！");
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("确定退出该账号吗？")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击确定按钮后的操作
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear().apply();

                                Intent intent = new Intent(getContext(), WelcomeActivity.class);
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击取消按钮后的操作
                                dialog.cancel(); // 关闭对话框
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        return settingFragment;
    }
}