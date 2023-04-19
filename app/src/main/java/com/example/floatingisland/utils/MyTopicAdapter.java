package com.example.floatingisland.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.ThereActivity;
import com.example.floatingisland.activity.TwoActivity;
import com.example.floatingisland.fragment.editInfoFragment;

import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

public class MyTopicAdapter extends RecyclerView.Adapter<MyTopicAdapter.MyViewHolder> {

    private List<Map<String, Object>> list;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private Activity mActivity;
    private boolean mAddJudgment;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        // 定义ViewHolder中的控件
        SuperTextView tpview;


        public MyViewHolder(View itemView) {
            super(itemView);
            // 初始化ViewHolder中的控件
            tpview = (SuperTextView) itemView.findViewById(R.id.tpview);

        }
    }

    public MyTopicAdapter(Context context, List<Map<String, Object>> list, RecyclerView recyclerView, Activity Activity, boolean Addjudgment) {
        this.mContext = context;
        this.list = list;
        this.mRecyclerView = recyclerView;
        this.mActivity = Activity;
        this.mAddJudgment = Addjudgment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 加载ViewHolder的布局文件并创建ViewHolder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_topic, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        // 获取数据并设置到ViewHolder中的控件上
        holder.tpview.setLeftTopString(list.get(position).get("name").toString());

        int cornerRadius = 20;
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transforms(new CenterCrop(), new RoundedCorners(cornerRadius));
        requestOptions.override(128, 128);
        Glide.with(mContext)
                .load(list.get(position).get("imageurl").toString())
                .apply(requestOptions)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.tpview.setLeftIcon(resource);
                    }
                });

        if(mAddJudgment){
            //点击事件
            holder.tpview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tid=list.get(position).get("tid").toString();
                    String name=list.get(position).get("name").toString();
                    String imageurl=list.get(position).get("imageurl").toString();

                    SharedPreferences sharedPreferences = mActivity.getSharedPreferences("TopicChooseInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("tid", tid);
                    editor.putString("name", name);
                    editor.putString("imageurl", imageurl);
                    editor.apply();

                    mActivity.finish();
                }
            });
        }else{
            //点击事件
            holder.tpview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tid=list.get(position).get("tid").toString();
                    Intent intent = new Intent(mContext, TwoActivity.class);
                    intent.putExtra("jumpcode", 1);
                    intent.putExtra("tid", tid);
                    mContext.startActivity(intent);
                    mActivity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        // 返回数据项的数量
        return list.size();
    }

}
