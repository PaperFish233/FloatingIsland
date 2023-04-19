package com.example.floatingisland.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.floatingisland.R;
import com.example.floatingisland.entity.Posts;
import com.example.floatingisland.entity.Topic;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.MyInfoPostsAdapter;
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


public class chooseTopicFragment extends Fragment {

    private View chooseTopicFragment;
    private RecyclerView recyclerView;
    private MyPostsAdapter adapter;

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

        chooseTopicFragment = inflater.inflate(R.layout.fragment_choosetopic, container, false);

        Toolbar toolbar = chooseTopicFragment.findViewById(R.id.Toolbar_choosetopic);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

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
                // 获取 RecyclerView 控件
                recyclerView = chooseTopicFragment.findViewById(R.id.recycler_view);
                // 创建 LinearLayoutManager 对象，设置为垂直方向
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                // 绑定 LayoutManager
                recyclerView.setLayoutManager(layoutManager);

                // 绑定数据适配器MyAdapter
                MyTopicAdapter MyTopicAdapter = new MyTopicAdapter(getContext(),list,recyclerView,getActivity(),true);
                recyclerView.setAdapter(MyTopicAdapter);

            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });


        return chooseTopicFragment;
    }
}