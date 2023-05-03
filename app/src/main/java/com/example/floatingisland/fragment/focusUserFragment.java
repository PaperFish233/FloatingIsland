package com.example.floatingisland.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.floatingisland.R;
import com.example.floatingisland.entity.Users;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.MyUserAdapter;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.MyToast;

public class focusUserFragment extends Fragment {

    private View focusUserFragment;
    private RecyclerView recyclerView;

    //将数据封装成数据源
    List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        focusUserFragment = inflater.inflate(R.layout.fragment_focususer, container, false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        Toolbar toolbar = focusUserFragment.findViewById(R.id.Toolbar_focususer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

        // 获取控件
        recyclerView = focusUserFragment.findViewById(R.id.recycler_view);
        ImageView isempty = focusUserFragment.findViewById(R.id.isempty);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        //接收传递值
        Bundle bundle = getArguments();
        Boolean isFacusUser = true;
        if (bundle != null) {
            isFacusUser = bundle.getBoolean("isFacusUser");
        }

        if(isFacusUser){
            toolbar.setNavigationIcon(R.mipmap.logo_focususer);
            //获取关注用户信息
            HashMap<String, String> params = new HashMap<>();
            params.put("uaccount", loginInfo);
            OkHttp.post(getContext(), Constant.getFacusUser, params, new OkCallback<Result<List<Users>>>() {
                @Override
                public void onResponse(Result<List<Users>> response) {
                    for (Users datum : response.getData()) {
                        Map<String,Object> map=new HashMap<String, Object>();
                        map.put("nickname",datum.getUnickname());
                        map.put("avatarurl",datum.getUavatarurl());
                        map.put("uaccount",datum.getUaccount());
                        list.add(map);
                    }

                    // 创建 LinearLayoutManager 对象，设置为垂直方向
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    // 绑定 LayoutManager
                    recyclerView.setLayoutManager(layoutManager);

                    //无关注页面显示标语
                    if(list.isEmpty()){
                        isempty.setImageResource(R.mipmap.isempty1);
                        isempty.setVisibility(View.VISIBLE);
                    }else {
                        isempty.setVisibility(View.GONE);
                        // 绑定数据适配器MyAdapter
                        MyUserAdapter MyFocusUserAdapter = new MyUserAdapter(getContext(), list, recyclerView, getActivity());
                        recyclerView.setAdapter(MyFocusUserAdapter);
                    }
                }

                @Override
                public void onFailure(String state, String msg) {
                    MyToast.errorBig("连接服务器超时！");
                }
            });
        }else{
            toolbar.setNavigationIcon(R.mipmap.logo_userfocus);
            //获取粉丝用户信息
            HashMap<String, String> params = new HashMap<>();
            params.put("uaccount", loginInfo);
            OkHttp.post(getContext(), Constant.getUserFacus, params, new OkCallback<Result<List<Users>>>() {
                @Override
                public void onResponse(Result<List<Users>> response) {
                    for (Users datum : response.getData()) {
                        Map<String,Object> map=new HashMap<String, Object>();
                        map.put("nickname",datum.getUnickname());
                        map.put("avatarurl",datum.getUavatarurl());
                        map.put("uaccount",datum.getUaccount());
                        list.add(map);
                    }

                    // 创建 LinearLayoutManager 对象，设置为垂直方向
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    // 绑定 LayoutManager
                    recyclerView.setLayoutManager(layoutManager);

                    //无关注页面显示标语
                    if(list.isEmpty()){
                        isempty.setImageResource(R.mipmap.isempty6);
                        isempty.setVisibility(View.VISIBLE);
                    }else {
                        isempty.setVisibility(View.GONE);
                        // 绑定数据适配器MyAdapter
                        MyUserAdapter MyUserAdapter = new MyUserAdapter(getContext(), list, recyclerView, getActivity());
                        recyclerView.setAdapter(MyUserAdapter);
                    }
                }

                @Override
                public void onFailure(String state, String msg) {
                    MyToast.errorBig("连接服务器超时！");
                }
            });
        }




        return focusUserFragment;
    }
}