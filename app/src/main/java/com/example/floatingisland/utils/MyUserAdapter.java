package com.example.floatingisland.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.OneActivity;

import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

public class MyUserAdapter extends RecyclerView.Adapter<MyUserAdapter.MyViewHolder> {

    private List<Map<String, Object>> list;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private Activity mActivity;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        // 定义ViewHolder中的控件
        SuperTextView tpview;


        public MyViewHolder(View itemView) {
            super(itemView);
            // 初始化ViewHolder中的控件
            tpview = (SuperTextView) itemView.findViewById(R.id.tpview);

        }
    }

    public MyUserAdapter(Context context, List<Map<String, Object>> list, RecyclerView recyclerView, Activity Activity) {
        this.mContext = context;
        this.list = list;
        this.mRecyclerView = recyclerView;
        this.mActivity = Activity;
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
        holder.tpview.setLeftTopString(list.get(position).get("nickname").toString());

        Glide.with(mContext).load(list.get(position).get("avatarurl").toString())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(100))
                        .override(100, 100))
                .transform(new CircleTransformation())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.tpview.setLeftIcon(resource);
                    }
                });

        //点击事件
        holder.tpview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String faccount = list.get(position).get("uaccount").toString();
                SharedPreferences sharedPreferences = mActivity.getSharedPreferences("userInfoPid", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("faccount", faccount);
                editor.apply();

                Intent intent = new Intent(mContext, OneActivity.class);
                intent.putExtra("jumpcode", 3);
                mActivity.startActivity(intent);
                mActivity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
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
