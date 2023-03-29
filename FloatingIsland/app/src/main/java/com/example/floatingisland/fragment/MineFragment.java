package com.example.floatingisland.fragment;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.floatingisland.R;
import com.example.floatingisland.entity.Posts;
import com.example.floatingisland.entity.Users;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineFragment extends Fragment {

    private View MineFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        MineFragment = inflater.inflate(R.layout.fragment_mine,container,false);

        SuperTextView avatar = MineFragment.findViewById(R.id.avatar);
        SuperTextView nickname = MineFragment.findViewById(R.id.nickname);
        SuperTextView account = MineFragment.findViewById(R.id.account);
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
                for (Users users : response.getData()) {
                    Uavatarurl = users.getUavatarurl();
                    Unickname = users.getUnickname();
                    Uaccount = users.getUaccount();
                }

                nickname.setRightString(Unickname);
                account.setRightString(Uaccount);

            }

            @Override
            public void onFailure(String state, String msg) {
                Toast.makeText(getContext(), "连接服务器失败！", Toast.LENGTH_SHORT).show();
            }
        });





        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "666！", Toast.LENGTH_SHORT).show();

            }
        });

        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "666！", Toast.LENGTH_SHORT).show();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "666！", Toast.LENGTH_SHORT).show();
            }
        });

        mineposts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "666！", Toast.LENGTH_SHORT).show();
            }
        });

        minecollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "666！", Toast.LENGTH_SHORT).show();
            }
        });

        minecollectionpeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "666！", Toast.LENGTH_SHORT).show();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "666！", Toast.LENGTH_SHORT).show();
            }
        });









        return MineFragment;
    }

}