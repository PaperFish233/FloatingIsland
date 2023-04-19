package com.example.floatingisland.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.MainActivity;
import com.example.floatingisland.activity.TwoActivity;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

import es.dmoral.toasty.MyToast;


public class addPostsFragment extends Fragment {

    private View addpostsFragment;

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        addpostsFragment = inflater.inflate(R.layout.fragment_addposts, container, false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        Toolbar toolbar = addpostsFragment.findViewById(R.id.Toolbar_addposts);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        MaterialEditText connect = addpostsFragment.findViewById(R.id.connect);
        MaterialEditText imageurl = addpostsFragment.findViewById(R.id.imageurl);
        TextView git = addpostsFragment.findViewById(R.id.git);
        SuperTextView topic = addpostsFragment.findViewById(R.id.topic);

        topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TwoActivity.class);
                intent.putExtra("jumpcode", 2);
                getContext().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
            }
        });

        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TopicChooseInfo", Context.MODE_PRIVATE);
                String tid = sharedPreferences.getString("tid", "");

                HashMap<String, String> params = new HashMap<>();
                params.put("tid", tid);
                params.put("uaccount", loginInfo);
                params.put("pconnect", connect.getText().toString());
                params.put("pimageurl", imageurl.getText().toString());
                if(connect.getText().toString().equals("")) {
                    MyToast.errorBig("不能发布空的内容哦！");
                }else{
                OkHttp.post(getContext(), Constant.insertPosts, params, new OkCallback<Result>() {
                    @Override
                    public void onResponse(Result response) {
                        if(response.getMessage().equals("发布成功")){
                            MyToast.successBig(response.getMessage());
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                            SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("TopicChooseInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                            editor1.clear().apply();
                            activity.onBackPressed();//销毁自己
                        }else{
                            MyToast.errorBig(response.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(String state, String msg) {
                        MyToast.errorBig("连接服务器超时！");
                    }
                });
                }
            }
        });

        return addpostsFragment;

    }

}