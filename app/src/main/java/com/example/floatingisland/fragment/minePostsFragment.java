package com.example.floatingisland.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.MainActivity;
import com.example.floatingisland.activity.ThereActivity;
import com.example.floatingisland.entity.Posts;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class minePostsFragment extends Fragment {

    private View minePostsFragment;

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

        minePostsFragment = inflater.inflate(R.layout.fragment_mineposts, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        Toolbar toolbar = minePostsFragment.findViewById(R.id.Toolbar_mineposts);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

        HashMap<String, String> params = new HashMap<>();
        params.put("uaccount", loginInfo);
        OkHttp.post(getContext(), Constant.getminePosts, params, new OkCallback<Result<List<Posts>>>() {
            @Override
            public void onResponse(Result<List<Posts>> response) {
                for (Posts datum : response.getData()) {
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("pid",datum.getPid());
                    map.put("likenum",datum.getLikenum());
                    map.put("avatarurl",datum.getAvatarurl());
                    map.put("nickname",datum.getNickname());
                    map.put("datetime",datum.getDate());
                    map.put("content",datum.getContent());
                    map.put("imageurl",datum.getImageurl());
                    map.put("topicname",datum.getTopicname());
                    list.add(map);

                }
                ListView listview=(ListView) minePostsFragment.findViewById(R.id.listView);
                listview.setAdapter(new minePostsFragment.MyAdapter());
            }

            @Override
            public void onFailure(String state, String msg) {
                Toast.makeText(getContext(), "连接服务器失败！", Toast.LENGTH_SHORT).show();
            }
        });

        final RefreshLayout refreshLayout = minePostsFragment.findViewById(R.id.refreshLayout);
        //设置 Header 为 经典 样式
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();//传入false表示刷新失败
                Intent intent = new Intent(getContext(), ThereActivity.class);
                intent.putExtra("jumpcode",1);
                startActivity(intent);
                activity.overridePendingTransition(0,0);
                activity.onBackPressed();//销毁自己

            }
        });


        return minePostsFragment;

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            minePostsFragment.ViewHolder mHolder;
            if(convertView==null){
                view= LayoutInflater.from(minePostsFragment.getContext()).inflate(R.layout.list_item,null);
                mHolder=new minePostsFragment.ViewHolder();
                mHolder.card_pid=(TextView)view.findViewById(R.id.pid);
                mHolder.card_avatar=(ImageView)view.findViewById(R.id.avatar);
                mHolder.card_nickname=(TextView)view.findViewById(R.id.nickname);
                mHolder.card_datetime=(TextView)view.findViewById(R.id.datetime);
                mHolder.card_image=(ImageView)view.findViewById(R.id.image);
                mHolder.card_content=(TextView)view.findViewById(R.id.content);
                mHolder.card_topic=(TextView)view.findViewById(R.id.topic);
                mHolder.collection=(ImageView)view.findViewById(R.id.collection);
                mHolder.likenum=(TextView)view.findViewById(R.id.likenum);
                mHolder.like=(ImageView)view.findViewById(R.id.like);
                mHolder.comment=(ImageView)view.findViewById(R.id.comment);
                mHolder.focus=(ImageView)view.findViewById(R.id.focus);
                view.setTag(mHolder);  //将ViewHolder存储在View中
            }else {
                view=convertView;
                mHolder=(minePostsFragment.ViewHolder)view.getTag();  //重新获得ViewHolder
            }

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
            String loginInfo = sharedPreferences.getString("account", "");

            mHolder.focus.setVisibility(View.INVISIBLE);//设置不可见

            mHolder.card_pid.setText(list.get(position).get("pid").toString());
            mHolder.likenum.setText(list.get(position).get("likenum").toString());
            Glide.with(getContext()).load(list.get(position).get("avatarurl")).apply(RequestOptions.bitmapTransform(new RoundedCorners(100)).override(100, 100)).into(mHolder.card_avatar);
            mHolder.card_nickname.setText("  "+list.get(position).get("nickname").toString());
            mHolder.card_datetime.setText("  发布于 "+list.get(position).get("datetime").toString());
            mHolder.card_content.setText(list.get(position).get("content").toString());
            Glide.with(getContext()).load(list.get(position).get("imageurl")).apply(RequestOptions.bitmapTransform(new RoundedCorners(20)).override(1280, 720)).into(mHolder.card_image);
            mHolder.card_topic.setText("#"+list.get(position).get("topicname").toString());


            //点赞状态检测
            HashMap<String, String> params = new HashMap<>();
            int pid=Integer.parseInt(mHolder.card_pid.getText().toString());
            params.put("pid", String.valueOf(pid));
            params.put("uaccount", loginInfo);
            OkHttp.post(getContext(), Constant.selectlike, params, new OkCallback<Result>() {
                @Override
                public void onResponse(Result response) {
                    if(response.getMessage().equals("已点赞")){
                        mHolder.like.setImageResource(R.mipmap.like1);
                    }else if(response.getMessage().equals("未点赞")){
                        mHolder.like.setImageResource(R.mipmap.like2);
                    }
                }

                @Override
                public void onFailure(String state, String msg) {
                    Toast.makeText(getContext(), "连接服务器失败！", Toast.LENGTH_SHORT).show();
                }
            });

            //收藏状态检测
            HashMap<String, String> params1 = new HashMap<>();
            int pid1=Integer.parseInt(mHolder.card_pid.getText().toString());
            params1.put("uaccount", loginInfo);
            params1.put("pid", String.valueOf(pid1));
            OkHttp.post(getContext(), Constant.selectcollection, params1, new OkCallback<Result>() {
                @Override
                public void onResponse(Result response) {
                    if(response.getMessage().equals("已收藏")){
                        mHolder.collection.setImageResource(R.mipmap.collection1);
                    }else if(response.getMessage().equals("未收藏")){
                        mHolder.collection.setImageResource(R.mipmap.collection2);
                    }
                }

                @Override
                public void onFailure(String state, String msg) {
                    Toast.makeText(getContext(), "连接服务器失败！", Toast.LENGTH_SHORT).show();
                }
            });

            //点赞按钮点击事件
            mHolder.like.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int pid=Integer.parseInt(mHolder.card_pid.getText().toString());
                    HashMap<String, String> params = new HashMap<>();
                    params.put("pid", String.valueOf(pid));
                    params.put("uaccount", loginInfo);
                    OkHttp.post(getContext(), Constant.like, params, new OkCallback<Result>() {
                        @Override
                        public void onResponse(Result response) {
                            if(response.getMessage().equals("点赞成功")){
                                mHolder.like.setImageResource(R.mipmap.like1);
                                String i = String.valueOf(Integer.parseInt(mHolder.likenum.getText().toString()) + 1);
                                mHolder.likenum.setText(i);
                            }else if(response.getMessage().equals("取消点赞成功")){
                                mHolder.like.setImageResource(R.mipmap.like2);
                                String i = String.valueOf(Integer.parseInt(mHolder.likenum.getText().toString()) - 1);
                                mHolder.likenum.setText(i);
                            }

                        }

                        @Override
                        public void onFailure(String state, String msg) {
                            Toast.makeText(getContext(), "连接服务器失败！", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

            //收藏按钮点击事件
            mHolder.collection.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int pid=Integer.parseInt(mHolder.card_pid.getText().toString());
                    HashMap<String, String> params = new HashMap<>();
                    params.put("pid", String.valueOf(pid));
                    params.put("uaccount", loginInfo);
                    OkHttp.post(getContext(), Constant.collection, params, new OkCallback<Result>() {
                        @Override
                        public void onResponse(Result response) {
                            Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                            if(response.getMessage().equals("收藏成功")){
                                mHolder.collection.setImageResource(R.mipmap.collection1);
                            }else if(response.getMessage().equals("取消收藏成功")){
                                mHolder.collection.setImageResource(R.mipmap.collection2);
                            }

                        }

                        @Override
                        public void onFailure(String state, String msg) {
                            Toast.makeText(getContext(), "连接服务器失败！", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

            //评论按钮点击事件
            mHolder.comment.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    // 创建MyBottomSheetDialogFragment的实例
                    CommentBottomDialog bottomSheet = new CommentBottomDialog();
                    // 显示MyBottomSheetDialogFragment
                    bottomSheet.show(getFragmentManager(), "bottomDialog");

                }
            });

            return view;
        }
    }

    class ViewHolder{
        TextView card_pid;
        ImageView card_avatar;
        TextView card_nickname;
        TextView card_datetime;
        ImageView card_image;
        TextView card_content;
        TextView card_topic;
        TextView likenum;
        ImageView like;
        ImageView collection;
        ImageView comment;
        ImageView focus;
    }

}