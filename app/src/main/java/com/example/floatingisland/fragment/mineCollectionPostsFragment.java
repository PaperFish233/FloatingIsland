package com.example.floatingisland.fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
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
import com.example.floatingisland.activity.ThereActivity;
import com.example.floatingisland.entity.Posts;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import es.dmoral.toasty.MyToast;


public class mineCollectionPostsFragment extends Fragment {

    private View mineCollectionPostsFragment;
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    //将数据封装成数据源
    List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();

    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mineCollectionPostsFragment = inflater.inflate(R.layout.fragment_minecollectionposts, container, false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        Toolbar toolbar = mineCollectionPostsFragment.findViewById(R.id.Toolbar_minecollectionposts);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

        HashMap<String, String> params = new HashMap<>();
        params.put("uaccount", loginInfo);
        OkHttp.post(getContext(), Constant.getmineCollectionPosts, params, new OkCallback<Result<List<Posts>>>() {
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
                // 获取 RecyclerView 控件
                recyclerView = mineCollectionPostsFragment.findViewById(R.id.recycler_view);
                // 创建 LinearLayoutManager 对象，设置为垂直方向
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                // 绑定 LayoutManager
                recyclerView.setLayoutManager(layoutManager);
                // 初始化 RecyclerView 适配器和数据
                adapter = new mineCollectionPostsFragment.MyAdapter(list);
                recyclerView.setAdapter(adapter);
                // 创建 SpaceItemDecoration 实例，设置5dp的间距
                SpaceItemDecoration decoration = new SpaceItemDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
                // 添加
                recyclerView.addItemDecoration(decoration);
            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });


        final RefreshLayout refreshLayout = mineCollectionPostsFragment.findViewById(R.id.refreshLayout);
        //设置 Header 为 经典 样式
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();//传入false表示刷新失败
                getFragmentManager().beginTransaction()
                        .replace(R.id.thereLayout, new mineCollectionPostsFragment())
                        .commit();

            }
        });

        return mineCollectionPostsFragment;
    }

    public class MyAdapter extends RecyclerView.Adapter<mineCollectionPostsFragment.MyAdapter.ViewHolder> {

        private List<Map<String, Object>> list;

        public class ViewHolder extends RecyclerView.ViewHolder {
            // 定义ViewHolder中的控件
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
            JzvdStd jz_video;

            public ViewHolder(View itemView) {
                super(itemView);
                // 初始化ViewHolder中的控件
                card_pid = (TextView) itemView.findViewById(R.id.pid);
                card_avatar = (ImageView) itemView.findViewById(R.id.avatar);
                card_nickname = (TextView) itemView.findViewById(R.id.nickname);
                card_datetime = (TextView) itemView.findViewById(R.id.datetime);
                card_image = (ImageView) itemView.findViewById(R.id.image);
                card_content = (TextView) itemView.findViewById(R.id.content);
                card_topic = (TextView) itemView.findViewById(R.id.topic);
                likenum = (TextView) itemView.findViewById(R.id.likenum);
                like = (ImageView) itemView.findViewById(R.id.like);
                collection = (ImageView) itemView.findViewById(R.id.collection);
                comment = (ImageView) itemView.findViewById(R.id.comment);
                focus = (ImageView) itemView.findViewById(R.id.focus);
                jz_video = (JzvdStd) itemView.findViewById(R.id.jz_video);
            }
        }

        public MyAdapter(List<Map<String, Object>> list) {
            this.list = list;
        }

        @Override
        public mineCollectionPostsFragment.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // 加载ViewHolder的布局文件并创建ViewHolder
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            mineCollectionPostsFragment.MyAdapter.ViewHolder vh = new mineCollectionPostsFragment.MyAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(mineCollectionPostsFragment.MyAdapter.ViewHolder holder, int position) {

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
            String loginInfo = sharedPreferences.getString("account", "");

            // 获取数据并设置到ViewHolder中的控件上
            holder.card_pid.setText(list.get(position).get("pid").toString());
            holder.likenum.setText(list.get(position).get("likenum").toString());
            Glide.with(getContext()).load(list.get(position).get("avatarurl")).apply(RequestOptions.bitmapTransform(new RoundedCorners(100)).override(100, 100)).into(holder.card_avatar);
            holder.card_nickname.setText("  "+list.get(position).get("nickname").toString());
            holder.card_datetime.setText("  发布于 "+list.get(position).get("datetime").toString());
            holder.card_content.setText(list.get(position).get("content").toString());
            holder.card_topic.setText("#"+list.get(position).get("topicname").toString());

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                private int mFirstVisibleItemPosition = -1;
                private int mVisibleItemCount = -1;
                private boolean mIsScrolling = false;

                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING ||
                            newState == RecyclerView.SCROLL_STATE_SETTLING) {
                        // 正在拖拽或正在惯性滑动，暂停播放
                        Jzvd.goOnPlayOnPause();
                        mIsScrolling = true;
                    } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        // 滑动停止，恢复播放
                        LinearLayoutManager layoutManager =
                                (LinearLayoutManager) recyclerView.getLayoutManager();
                        mFirstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                        mVisibleItemCount = layoutManager.getChildCount();
                        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                        if (mFirstVisibleItemPosition >= 0 && lastVisibleItemPosition >= 0) {
                            for (int i = mFirstVisibleItemPosition; i <= lastVisibleItemPosition; i++) {
                                View itemView = layoutManager.findViewByPosition(i);
                                if (itemView != null) {
                                    JzvdStd player = (JzvdStd) itemView.findViewById(R.id.jz_video);
                                    if (player != null &&
                                            i >= mFirstVisibleItemPosition &&
                                            i < mFirstVisibleItemPosition + mVisibleItemCount) {
                                        // 当前视频可见，恢复播放
                                        if (player != null && player.state == Jzvd.STATE_PAUSE) {
                                            player.startVideo();
                                        }
                                    } else {
                                        // 当前视频不可见，释放资源
                                        player.releaseAllVideos();
                                    }
                                }
                            }
                        }
                        mIsScrolling = false;
                    }
                }
            });

            //图文或视频检测
            String url = list.get(position).get("imageurl").toString();
            if(url.endsWith(".gif") || url.endsWith(".jpg") || url.endsWith(".png")) {
                holder.jz_video.setVisibility(View.GONE);
                holder.card_image.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(list.get(position).get("imageurl")).apply(RequestOptions.bitmapTransform(new RoundedCorners(20)).override(1280, 720)).into(holder.card_image);
            } else if(url.endsWith(".mp4") || url.endsWith(".avi") || url.endsWith(".mov")) {
                holder.card_image.setVisibility(View.GONE);
                holder.jz_video.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(url).centerCrop().into(holder.jz_video.posterImageView);
                holder.jz_video.setUp(url, "");
            }

            //关注状态检测
            HashMap<String, String> params2 = new HashMap<>();
            int pid2=Integer.parseInt(holder.card_pid.getText().toString());
            params2.put("pid", String.valueOf(pid2));
            params2.put("uaccount", loginInfo);
            OkHttp.post(getContext(), Constant.selectfocus, params2, new OkCallback<Result>() {
                @Override
                public void onResponse(Result response) {
                    if(response.getMessage().equals("已关注")){
                        holder.focus.setVisibility(View.INVISIBLE);//设置不可见
                    }else{
                        holder.focus.setVisibility(View.VISIBLE);//设置可见
                    }
                }

                @Override
                public void onFailure(String state, String msg) {
                    MyToast.errorBig("连接服务器超时！");
                }
            });

            //点赞状态检测
            HashMap<String, String> params = new HashMap<>();
            int pid=Integer.parseInt(holder.card_pid.getText().toString());
            params.put("pid", String.valueOf(pid));
            params.put("uaccount", loginInfo);
            OkHttp.post(getContext(), Constant.selectlike, params, new OkCallback<Result>() {
                @Override
                public void onResponse(Result response) {
                    if(response.getMessage().equals("已点赞")){
                        holder.like.setImageResource(R.mipmap.like1);
                    }else if(response.getMessage().equals("未点赞")){
                        holder.like.setImageResource(R.mipmap.like2);
                    }
                }

                @Override
                public void onFailure(String state, String msg) {
                    MyToast.errorBig("连接服务器超时！");
                }
            });

            //收藏状态检测
            HashMap<String, String> params1 = new HashMap<>();
            int pid1=Integer.parseInt(holder.card_pid.getText().toString());
            params1.put("pid", String.valueOf(pid1));
            params1.put("uaccount", loginInfo);
            OkHttp.post(getContext(), Constant.selectcollection, params1, new OkCallback<Result>() {
                @Override
                public void onResponse(Result response) {
                    if(response.getMessage().equals("已收藏")){
                        holder.collection.setImageResource(R.mipmap.collection1);
                    }else if(response.getMessage().equals("未收藏")){
                        holder.collection.setImageResource(R.mipmap.collection2);
                    }
                }

                @Override
                public void onFailure(String state, String msg) {
                    MyToast.errorBig("连接服务器超时！");
                }
            });

            //关注按钮点击事件
            holder.focus.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int pid=Integer.parseInt(holder.card_pid.getText().toString());
                    HashMap<String, String> params = new HashMap<>();
                    params.put("pid", String.valueOf(pid));
                    params.put("uaccount", loginInfo);
                    OkHttp.post(getContext(), Constant.focus, params, new OkCallback<Result>() {
                        @Override
                        public void onResponse(Result response) {
                            if(response.getMessage().equals("关注成功")){
                                MyToast.successBig(response.getMessage());
                                holder.focus.setVisibility(View.INVISIBLE);//设置不可见
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
            });

            //点赞按钮点击事件
            holder.like.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int pid=Integer.parseInt(holder.card_pid.getText().toString());
                    HashMap<String, String> params = new HashMap<>();
                    params.put("pid", String.valueOf(pid));
                    params.put("uaccount", loginInfo);
                    OkHttp.post(getContext(), Constant.like, params, new OkCallback<Result>() {
                        @Override
                        public void onResponse(Result response) {
                            if(response.getMessage().equals("点赞成功")){
                                holder.like.setImageResource(R.mipmap.like1);
                                String i = String.valueOf(Integer.parseInt(holder.likenum.getText().toString()) + 1);
                                holder.likenum.setText(i);
                            }else if(response.getMessage().equals("取消点赞成功")){
                                holder.like.setImageResource(R.mipmap.like2);
                                String i = String.valueOf(Integer.parseInt(holder.likenum.getText().toString()) - 1);
                                holder.likenum.setText(i);

                            }

                        }

                        @Override
                        public void onFailure(String state, String msg) {
                            MyToast.errorBig("连接服务器超时！");
                        }
                    });

                }
            });

            //收藏按钮点击事件
            holder.collection.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int pid=Integer.parseInt(holder.card_pid.getText().toString());
                    HashMap<String, String> params = new HashMap<>();
                    params.put("pid", String.valueOf(pid));
                    params.put("uaccount", loginInfo);
                    OkHttp.post(getContext(), Constant.collection, params, new OkCallback<Result>() {
                        @Override
                        public void onResponse(Result response) {
                            MyToast.successBig(response.getMessage());
                            if(response.getMessage().equals("收藏成功")){
                                holder.collection.setImageResource(R.mipmap.collection1);
                            }else if(response.getMessage().equals("取消收藏成功")){
                                holder.collection.setImageResource(R.mipmap.collection2);
                            }

                        }

                        @Override
                        public void onFailure(String state, String msg) {
                            MyToast.errorBig("连接服务器超时！");
                        }
                    });

                }
            });

            //评论按钮点击事件
            holder.comment.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    // 创建MyBottomSheetDialogFragment的实例
                    CommentBottomDialog bottomSheet = new CommentBottomDialog();
                    // 显示MyBottomSheetDialogFragment
                    bottomSheet.show(getFragmentManager(), "bottomDialog");

                }
            });

        }

        @Override
        public int getItemCount() {
            // 返回数据项的数量
            return list.size();
        }
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        // 设置第二个 item 和后续 item 之间的间距
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) > 0) {
                outRect.top = space;
            }
        }
    }

}