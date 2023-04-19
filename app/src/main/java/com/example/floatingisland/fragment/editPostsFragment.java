package com.example.floatingisland.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.MainActivity;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

import es.dmoral.toasty.MyToast;

public class editPostsFragment extends Fragment {

    private View editPostsFragment;

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        editPostsFragment = inflater.inflate(R.layout.fragment_editposts, container, false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        Toolbar toolbar = editPostsFragment.findViewById(R.id.Toolbar_editposts);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        //接收传递值
        Bundle bundle = getArguments();
        String lastpid = "";
        String lastcontent = "";
        String lastimageurl = "";
        if (bundle != null) {
            lastpid = bundle.getString("lastpid");
            lastcontent = bundle.getString("lastcontent");
            lastimageurl = bundle.getString("lastimageurl");
        }

        MaterialEditText connect = editPostsFragment.findViewById(R.id.connect);
        MaterialEditText imageurl = editPostsFragment.findViewById(R.id.imageurl);
        TextView git = editPostsFragment.findViewById(R.id.git);
        SuperTextView topic = editPostsFragment.findViewById(R.id.topic);

        connect.setText(lastcontent);
        imageurl.setText(lastimageurl);

        String finalLastpid = lastpid;
        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> params = new HashMap<>();
                params.put("pid", finalLastpid);
                params.put("uaccount", loginInfo);
                params.put("pconnect", connect.getText().toString());
                params.put("pimageurl", imageurl.getText().toString());
                if(connect.getText().toString().isEmpty()) {
                    MyToast.errorBig("不能更新空的内容哦！");
                }else{
                    OkHttp.post(getContext(), Constant.updatePosts, params, new OkCallback<Result>() {
                        @Override
                        public void onResponse(Result response) {
                            if(response.getMessage().equals("更新成功")){
                                MyToast.successBig(response.getMessage());
                                activity.onBackPressed();

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

        return editPostsFragment;
    }
}