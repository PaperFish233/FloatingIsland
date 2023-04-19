package com.example.floatingisland.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.floatingisland.R;
import com.example.floatingisland.entity.Posts;
import com.example.floatingisland.entity.Topic;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.MyPostsAdapter;
import com.example.floatingisland.utils.MyTopicAdapter;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.MyToast;

public class DiscoverTPFragment extends Fragment {

    private View DiscoverTPFragment;
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

        DiscoverTPFragment = inflater.inflate(R.layout.fragment_discovertp, container, false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        // 获取 RecyclerView 控件
        recyclerView = DiscoverTPFragment.findViewById(R.id.recycler_view);


        OkHttp.post(getContext(), Constant.getTopic, null, new OkCallback<Result<List<Topic>>>() {
            @Override
            public void onResponse(Result<List<Topic>> response) {
                for (Topic datum : response.getData()) {
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("tid",datum.getTid());
                    map.put("name",datum.getTname());
                    map.put("imageurl",datum.getTimageurl());
                    map.put("signature",datum.getTsignature());
                    list.add(map);
                }

                // 创建 LinearLayoutManager 对象，设置为垂直方向
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                // 绑定 LayoutManager
                recyclerView.setLayoutManager(layoutManager);

                // 绑定数据适配器MyAdapter
                MyTopicAdapter MyTopicAdapter = new MyTopicAdapter(getContext(),list,recyclerView,getActivity(),false);
                recyclerView.setAdapter(MyTopicAdapter);

            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });


        return DiscoverTPFragment;
    }
}