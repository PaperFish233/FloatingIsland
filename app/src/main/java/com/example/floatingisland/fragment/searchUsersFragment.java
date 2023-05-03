package com.example.floatingisland.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

public class searchUsersFragment extends Fragment {

    private View searchUsersFragment;
    private RecyclerView recyclerView;

    //将数据封装成数据源
    List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        searchUsersFragment = inflater.inflate(R.layout.fragment_searchusers, container, false);

        // 获取控件
        recyclerView = searchUsersFragment.findViewById(R.id.recycler_view);
        TextView TextView_keyword = searchUsersFragment.findViewById(R.id.keyword);
        ImageView isempty = searchUsersFragment.findViewById(R.id.isempty);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("searchInfo", Context.MODE_PRIVATE);
        String keyword = sharedPreferences.getString("keyword", "");

        TextView_keyword.setText("关键词："+keyword);

        //获取用户信息
        HashMap<String, String> params = new HashMap<>();
        params.put("unickname", keyword);
        OkHttp.post(getContext(), Constant.getSearchUser, params, new OkCallback<Result<List<Users>>>() {
            @Override
            public void onResponse(Result<List<Users>> response) {
                for (Users datum : response.getData()) {
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("uaccount",datum.getUaccount());
                    map.put("nickname",datum.getUnickname());
                    map.put("avatarurl",datum.getUavatarurl());
                    list.add(map);
                }

                // 创建 LinearLayoutManager 对象，设置为垂直方向
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                // 绑定 LayoutManager
                recyclerView.setLayoutManager(layoutManager);

                //无搜索页面显示标语
                if(list.isEmpty()){
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


        return searchUsersFragment;
    }
}