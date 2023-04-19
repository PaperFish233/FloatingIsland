package com.example.floatingisland.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.floatingisland.R;
import com.example.floatingisland.activity.ThereActivity;
import com.example.floatingisland.entity.Posts;
import com.example.floatingisland.entity.Topic;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.MyRankingAdapter;
import com.example.floatingisland.utils.MyTopicAdapter;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.MyToast;

public class DiscoverRKFragment extends Fragment {

    private View DiscoverRKFragment;
    private RecyclerView recyclerView;
    private TextView rankingtext;

    //将数据封装成数据源
    List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    public void getlikePosts(){
        OkHttp.post(getContext(), Constant.getRankingPosts, null, new OkCallback<Result<List<Posts>>>() {
            @Override
            public void onResponse(Result<List<Posts>> response) {
                for (Posts datum : response.getData()) {
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("connect",datum.getContent());
                    map.put("likenum",datum.getLikenum());

                    list.add(map);
                }
                // 绑定数据适配器MyAdapter
                MyRankingAdapter MyRankingAdapter = new MyRankingAdapter(getContext(),list,recyclerView,getActivity());
                recyclerView.setAdapter(MyRankingAdapter);
            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });
    }

    public void getcollectionPosts(){
        OkHttp.post(getContext(), Constant.getRankingCollectionPosts, null, new OkCallback<Result<List<Posts>>>() {
            @Override
            public void onResponse(Result<List<Posts>> response) {
                for (Posts datum : response.getData()) {
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("connect",datum.getContent());
                    map.put("likenum",datum.getLikenum());

                    list.add(map);
                }

                // 绑定数据适配器MyAdapter
                MyRankingAdapter MyRankingAdapter = new MyRankingAdapter(getContext(),list,recyclerView,getActivity());
                recyclerView.setAdapter(MyRankingAdapter);
            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });
    }

    public void getcommentPosts(){
        OkHttp.post(getContext(), Constant.getRankingCommentPosts, null, new OkCallback<Result<List<Posts>>>() {
            @Override
            public void onResponse(Result<List<Posts>> response) {
                for (Posts datum : response.getData()) {
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("connect",datum.getContent());
                    map.put("likenum",datum.getLikenum());

                    list.add(map);
                }

                // 绑定数据适配器MyAdapter
                MyRankingAdapter MyRankingAdapter = new MyRankingAdapter(getContext(),list,recyclerView,getActivity());
                recyclerView.setAdapter(MyRankingAdapter);
            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DiscoverRKFragment = inflater.inflate(R.layout.fragment_discoverrk, container, false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        // 获取控件
        recyclerView = DiscoverRKFragment.findViewById(R.id.recycler_view);
        GridView gridview = DiscoverRKFragment.findViewById(R.id.gridview);
        rankingtext = DiscoverRKFragment.findViewById(R.id.rankingtext);

        gridview.setAdapter(new DiscoverRKFragment.MyGridViewAdapter());

        OkHttp.post(getContext(), Constant.getRankingPosts, null, new OkCallback<Result<List<Posts>>>() {
            @Override
            public void onResponse(Result<List<Posts>> response) {
                for (Posts datum : response.getData()) {
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("connect",datum.getContent());
                    map.put("likenum",datum.getLikenum());

                    list.add(map);
                }

                // 创建 LinearLayoutManager 对象，设置为垂直方向
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                // 绑定 LayoutManager
                recyclerView.setLayoutManager(layoutManager);

                // 绑定数据适配器MyAdapter
                MyRankingAdapter MyRankingAdapter = new MyRankingAdapter(getContext(),list,recyclerView,getActivity());
                recyclerView.setAdapter(MyRankingAdapter);

            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });


        return DiscoverRKFragment;
    }

    private class MyGridViewAdapter extends BaseAdapter {

        // 记录每个 item 的可点状态
        private boolean[] mClickableArray = {true, true, true};

        private final List<DiscoverRKFragment.Item> mData = Arrays.asList(
                new DiscoverRKFragment.Item(R.mipmap.like1, "点赞榜"),
                new DiscoverRKFragment.Item(R.mipmap.collection1, "收藏榜"),
                new DiscoverRKFragment.Item(R.mipmap.comment1, "评论榜")
        );

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DiscoverRKFragment.MyGridViewAdapter.ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.grid_item, parent, false);
                viewHolder = new DiscoverRKFragment.MyGridViewAdapter.ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (DiscoverRKFragment.MyGridViewAdapter.ViewHolder) convertView.getTag();
            }
            DiscoverRKFragment.Item item = mData.get(position);
            viewHolder.mImageView.setImageResource(item.imageResId);
            viewHolder.mTextView.setText(item.text);

            // 设置可点状态
            if (mClickableArray[position]) {
                convertView.setClickable(true);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 添加自定义的点击效果，比如修改背景颜色或者添加缩放效果等
                        v.setBackgroundResource(R.drawable.grid_item_bg);

                        switch (position){
                            case 0:
                                rankingtext.setText("点赞榜");
                                list.clear();
                                getlikePosts();
                                MyToast.successBig("榜单已切换至点赞榜");
                                break;
                            case 1:
                                rankingtext.setText("收藏榜");
                                list.clear();
                                getcollectionPosts();
                                MyToast.successBig("榜单已切换至收藏榜");
                                break;
                            case 2:
                                rankingtext.setText("评论榜");
                                list.clear();
                                getcommentPosts();
                                MyToast.successBig("榜单已切换至评论榜");
                                break;
                        }
                    }
                });
            } else {
                convertView.setClickable(false);
                convertView.setOnClickListener(null);
                convertView.setBackgroundResource(0);
            }

            return convertView;
        }

        private class ViewHolder {
            final ImageView mImageView;
            final TextView mTextView;

            ViewHolder(View view) {
                mImageView = view.findViewById(R.id.grid_item_image);
                mTextView = view.findViewById(R.id.grid_item_text);
            }
        }
    }

    private static class Item {
        final int imageResId;
        final String text;

        Item(int imageResId, String text) {
            this.imageResId = imageResId;
            this.text = text;
        }
    }

}