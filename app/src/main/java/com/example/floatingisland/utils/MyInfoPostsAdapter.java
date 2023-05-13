package com.example.floatingisland.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.PhotoActivity;
import com.example.floatingisland.activity.ThereActivity;
import com.example.floatingisland.activity.TwoActivity;
import com.example.floatingisland.fragment.CommentBottomDialog;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import es.dmoral.toasty.MyToast;
import kr.co.prnd.readmore.ReadMoreTextView;

public class MyInfoPostsAdapter extends RecyclerView.Adapter<MyInfoPostsAdapter.MyViewHolder> {

    private List<Map<String, Object>> list;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private Activity mActivity;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        // 定义ViewHolder中的控件
        TextView card_pid;
        ImageView card_avatar;
        TextView card_nickname;
        TextView card_datetime;
        ImageView more;
        ImageView card_image;
        ReadMoreTextView card_content;
        SuperTextView card_topic;
        TextView likenum;
        TextView collectionnum;
        TextView commentnum;
        ImageView like;
        ImageView collection;
        ImageView comment;
        JzvdStd jz_video;
        RelativeLayout userinfo;

        public MyViewHolder(View itemView) {
            super(itemView);
            // 初始化ViewHolder中的控件
            card_pid = (TextView) itemView.findViewById(R.id.pid);
            card_avatar = (ImageView) itemView.findViewById(R.id.avatar);
            card_nickname = (TextView) itemView.findViewById(R.id.nickname);
            card_datetime = (TextView) itemView.findViewById(R.id.datetime);
            more = (ImageView) itemView.findViewById(R.id.more);
            card_image = (ImageView) itemView.findViewById(R.id.image);
            card_content = (ReadMoreTextView) itemView.findViewById(R.id.content);
            card_topic = (SuperTextView) itemView.findViewById(R.id.topic);
            likenum = (TextView) itemView.findViewById(R.id.likenum);
            collectionnum = (TextView) itemView.findViewById(R.id.collectionnum);
            commentnum = (TextView) itemView.findViewById(R.id.commentnum);
            like = (ImageView) itemView.findViewById(R.id.like);
            collection = (ImageView) itemView.findViewById(R.id.collection);
            comment = (ImageView) itemView.findViewById(R.id.comment);
            jz_video = (JzvdStd) itemView.findViewById(R.id.jz_video);
            userinfo = (RelativeLayout) itemView.findViewById(R.id.userinfo);
        }
    }

    public MyInfoPostsAdapter(Context context, List<Map<String, Object>> list, RecyclerView recyclerView, Activity Activity) {
        this.mContext = context;
        this.list = list;
        this.mRecyclerView = recyclerView;
        this.mActivity = Activity;
    }

    public static FragmentManager getFragmentManager(Context context) {
        if (context instanceof AppCompatActivity) {
            return ((AppCompatActivity) context).getSupportFragmentManager();
        } else if (context instanceof FragmentActivity) {
            return ((FragmentActivity) context).getSupportFragmentManager();
        } else {
            throw new IllegalArgumentException("Context must be an instance of AppCompatActivity or FragmentActivity.");
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 加载ViewHolder的布局文件并创建ViewHolder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mineinfo, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        String lastcontent = (String) list.get(position).get("content");
        String lastimageurl = (String) list.get(position).get("imageurl");
        String lasttopicname = (String) list.get(position).get("topicname");
        String topicimageurl = list.get(position).get("topicimageurl").toString();

        // 获取数据并设置到ViewHolder中的控件上
        holder.card_pid.setText(list.get(position).get("pid").toString());
        holder.likenum.setText(list.get(position).get("likenum").toString());
        holder.collectionnum.setText(list.get(position).get("collectionnum").toString());
        holder.commentnum.setText(list.get(position).get("commentnum").toString());
        Glide.with(mContext).load(list.get(position).get("avatarurl").toString()).apply(RequestOptions.bitmapTransform(new RoundedCorners(100)).override(100, 100)).transform(new CircleTransformation()).into(holder.card_avatar);
        holder.card_nickname.setText("  " + list.get(position).get("nickname").toString());
        holder.card_datetime.setText("  发布于 " + list.get(position).get("datetime").toString());
        holder.card_content.setText(list.get(position).get("content").toString());

        holder.card_topic.setLeftString("#" + list.get(position).get("topicname").toString());

        int cornerRadius = 20;
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transforms(new CenterCrop(), new RoundedCorners(cornerRadius));
        requestOptions.override(80, 80);
        Glide.with(mContext)
                .load(list.get(position).get("topicimageurl").toString())
                .apply(requestOptions)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.card_topic.setLeftIcon(resource);
                    }
                });

        holder.card_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tid=list.get(position).get("topicid").toString();
                Intent intent = new Intent(mContext, TwoActivity.class);
                intent.putExtra("jumpcode", 1);
                intent.putExtra("tid", tid);
                mContext.startActivity(intent);
                mActivity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
            }
        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        String url = (String) list.get(position).get("imageurl");
        if (url == null || url.isEmpty()) {
            holder.jz_video.setVisibility(View.GONE);
            holder.card_image.setVisibility(View.GONE);
        } else {
            if (url.toLowerCase().endsWith(".gif") || url.toLowerCase().endsWith(".jpg") || url.toLowerCase().endsWith(".jpeg") || url.toLowerCase().endsWith(".png")) {
                holder.jz_video.setVisibility(View.GONE);
                holder.card_image.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(url).apply(RequestOptions.bitmapTransform(new RoundedCorners(20)).override(1280, 720)).into(holder.card_image);
            } else if (url.toLowerCase().endsWith(".mp4") || url.toLowerCase().endsWith(".avi") || url.toLowerCase().endsWith(".flv") || url.toLowerCase().endsWith(".mov")) {
                holder.card_image.setVisibility(View.GONE);
                holder.jz_video.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(url).centerCrop().into(holder.jz_video.posterImageView); //.placeholder(R.mipmap.placeholder)占位符
                holder.jz_video.setUp(url, "");
            } else {
                holder.card_image.setVisibility(View.GONE);
                holder.jz_video.setVisibility(View.GONE);
            }
        }

        //点赞状态检测
        HashMap<String, String> params = new HashMap<>();
        int pid=Integer.parseInt(holder.card_pid.getText().toString());
        params.put("pid", String.valueOf(pid));
        params.put("uaccount", loginInfo);
        OkHttp.post(mContext, Constant.selectlike, params, new OkCallback<Result>() {
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
        OkHttp.post(mContext, Constant.selectcollection, params1, new OkCallback<Result>() {
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

        // 创建一个列表数据源
        final String[] items = {"编辑", "删除", "取消"};

        int listposition = position;
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建一个 BottomSheetDialog
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
                // 创建一个列表视图
                ListView listView = new ListView(mContext);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.more_list_item, items);
                listView.setAdapter(adapter);

                // 列表点击事件
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        switch (position){
                            case 0:
                                // 关闭底部对话框
                                bottomSheetDialog.dismiss();

                                int pid=Integer.parseInt(holder.card_pid.getText().toString());
                                Intent intent = new Intent(mContext, ThereActivity.class);
                                intent.putExtra("jumpcode", 4);
                                intent.putExtra("lastpid", pid);
                                intent.putExtra("lastcontent", lastcontent);
                                intent.putExtra("lastimageurl", lastimageurl);
                                intent.putExtra("lasttopicname", lasttopicname);
                                intent.putExtra("topicimageurl", topicimageurl);
                                mContext.startActivity(intent);
                                mActivity.overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_from_left);
                                break;
                            case 1:
                                // 关闭底部对话框
                                bottomSheetDialog.dismiss();

                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setMessage("确定删除这条动态吗？")
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // 点击确定按钮后的操作
                                                int pid=Integer.parseInt(holder.card_pid.getText().toString());
                                                HashMap<String, String> params = new HashMap<>();
                                                params.put("pid", String.valueOf(pid));
                                                params.put("uaccount", loginInfo);
                                                OkHttp.post(mContext, Constant.deletePosts, params, new OkCallback<Result>() {
                                                    @Override
                                                    public void onResponse(Result response) {
                                                        if(response.getMessage().equals("删除成功")){
                                                            MyToast.successBig(response.getMessage());
                                                            list.remove(listposition);
                                                            notifyItemRemoved(listposition);
                                                        }else if(response.getMessage().equals("删除失败")){
                                                            MyToast.errorBig(response.getMessage());
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(String state, String msg) {
                                                        MyToast.errorBig("连接服务器超时！");
                                                    }
                                                });
                                            }
                                        })
                                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // 点击取消按钮后的操作
                                                dialog.cancel(); // 关闭对话框
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                break;
                            case 2:
                                // 关闭底部对话框
                                bottomSheetDialog.dismiss();
                                break;
                        }
                    }
                });

                // 将列表视图添加到底部对话框中
                bottomSheetDialog.setContentView(listView);
                // 显示底部对话框
                bottomSheetDialog.show();
            }
        });

        //点赞按钮点击事件
        holder.like.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int pid=Integer.parseInt(holder.card_pid.getText().toString());
                HashMap<String, String> params = new HashMap<>();
                params.put("pid", String.valueOf(pid));
                params.put("uaccount", loginInfo);
                OkHttp.post(mContext, Constant.like, params, new OkCallback<Result>() {
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
                OkHttp.post(mContext, Constant.collection, params, new OkCallback<Result>() {
                    @Override
                    public void onResponse(Result response) {
                        MyToast.successBig(response.getMessage());
                        if(response.getMessage().equals("收藏成功")){
                            holder.collection.setImageResource(R.mipmap.collection1);
                            String i = String.valueOf(Integer.parseInt(holder.collectionnum.getText().toString()) + 1);
                            holder.collectionnum.setText(i);
                        }else if(response.getMessage().equals("取消收藏成功")){
                            holder.collection.setImageResource(R.mipmap.collection2);
                            String i = String.valueOf(Integer.parseInt(holder.collectionnum.getText().toString()) - 1);
                            holder.collectionnum.setText(i);
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
                int pid=Integer.parseInt(holder.card_pid.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putInt("pid", pid);

                // 创建MyBottomSheetDialogFragment的实例
                CommentBottomDialog CommentBottomDialog = new CommentBottomDialog();
                CommentBottomDialog.setArguments(bundle);

                // 显示MyBottomSheetDialogFragment
                CommentBottomDialog.show(getFragmentManager(mContext), "bottomDialog");

            }
        });


        holder.card_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PhotoActivity.class);
                intent.setData(Uri.parse(url));
                mContext.startActivity(intent);
                mActivity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });


    }



    @Override
    public int getItemCount() {
        // 返回数据项的数量
        return list.size();
    }

    // 定义自定义的 CircleTransformation
    public class CircleTransformation extends BitmapTransformation {
        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            Bitmap bitmap = TransformationUtils.circleCrop(pool, toTransform, outWidth, outHeight);
            return bitmap;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            // do nothing
        }
    }

}
