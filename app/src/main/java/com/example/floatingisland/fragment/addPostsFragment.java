package com.example.floatingisland.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.floatingisland.R;
import com.example.floatingisland.activity.MainActivity;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;


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

        Toolbar toolbar = addpostsFragment.findViewById(R.id.Toolbar_addposts);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

        MaterialEditText connect = addpostsFragment.findViewById(R.id.connect);
        MaterialEditText imageurl = addpostsFragment.findViewById(R.id.imageurl);
        Button git = addpostsFragment.findViewById(R.id.git);
        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> params = new HashMap<>();
                String pconnect1 = connect.getText().toString();
                String pimageurl1 = imageurl.getText().toString();
                params.put("pconnect", pconnect1);
                params.put("pimageurl", pimageurl1);
                if(pconnect1.equals("") && pimageurl1.equals("")) {
                    Toast.makeText(getContext(), "不能发布空的内容哦！", Toast.LENGTH_SHORT).show();
                }else{
                OkHttp.post(getContext(), Constant.insertPosts, params, new OkCallback<Result>() {
                    @Override
                    public void onResponse(Result response) {
                            Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                            activity.onBackPressed();//销毁自己

                    }

                    @Override
                    public void onFailure(String state, String msg) {
                        Toast.makeText(getContext(), "连接服务器失败！", Toast.LENGTH_SHORT).show();
                    }
                });
                }
            }
        });

        return addpostsFragment;

    }

}