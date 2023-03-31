package com.example.floatingisland.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.OneActivity;
import com.example.floatingisland.activity.ThereActivity;
import com.example.floatingisland.entity.Users;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;

import java.util.HashMap;
import java.util.List;

public class MineFragment extends Fragment {

    private View MineFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        MineFragment = inflater.inflate(R.layout.fragment_mine,container,false);

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
        params.put("uaccount", "paperfish");
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
                Toast.makeText(getContext(), "连接服务器失败！", Toast.LENGTH_SHORT).show();
            }
        });



        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "点击头像！", Toast.LENGTH_SHORT).show();

            }
        });

        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "点击昵称！", Toast.LENGTH_SHORT).show();
            }
        });

        signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "点击简介！", Toast.LENGTH_SHORT).show();
            }
        });

        mineposts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThereActivity.class);
                intent.putExtra("jumpcode",1);
                startActivity(intent);
            }
        });

        minecollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThereActivity.class);
                intent.putExtra("jumpcode",2);
                startActivity(intent);
            }
        });

        minecollectionpeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "点击关注的人！", Toast.LENGTH_SHORT).show();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "点击退出登录！", Toast.LENGTH_SHORT).show();
            }
        });









        return MineFragment;
    }

}