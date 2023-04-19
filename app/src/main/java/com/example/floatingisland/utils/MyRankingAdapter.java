package com.example.floatingisland.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.OneActivity;
import com.example.floatingisland.activity.TwoActivity;

import java.util.List;
import java.util.Map;

public class MyRankingAdapter extends RecyclerView.Adapter<MyRankingAdapter.MyViewHolder> {

    private List<Map<String, Object>> list;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private Activity mActivity;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        // 定义ViewHolder中的控件
        TextView connect;
        TextView num;
        LinearLayout ll1;

        public MyViewHolder(View itemView) {
            super(itemView);
            // 初始化ViewHolder中的控件
            connect = (TextView) itemView.findViewById(R.id.connect);
            num = (TextView) itemView.findViewById(R.id.num);
            ll1 = (LinearLayout) itemView.findViewById(R.id.ll1);

        }
    }

    public MyRankingAdapter(Context context, List<Map<String, Object>> list, RecyclerView recyclerView, Activity Activity) {
        this.mContext = context;
        this.list = list;
        this.mRecyclerView = recyclerView;
        this.mActivity = Activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 加载ViewHolder的布局文件并创建ViewHolder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ranking, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        // 获取数据并设置到ViewHolder中的控件上
        holder.connect.setText(list.get(position).get("connect").toString());
        holder.num.setText(Integer.toString(position+1));

        holder.ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = list.get(position).get("connect").toString();
                SharedPreferences sharedPreferences = mActivity.getSharedPreferences("searchInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("keyword", keyword);
                editor.apply();

                Intent intent = new Intent(mContext, OneActivity.class);
                intent.putExtra("jumpcode",2);
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

}
