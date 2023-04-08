package com.example.floatingisland.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.floatingisland.R;

import java.util.List;
import java.util.Map;

public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.MyViewHolder>{

    private List<Map<String, Object>> list;
    private Context mContext;

    public MyCommentAdapter(Context context, List<Map<String, Object>> list) {
        this.mContext = context;
        this.list = list;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        // 定义ViewHolder中的控件
        ImageView avatar;
        TextView nickname;
        TextView datetime;
        TextView content;
        TextView empty_view;


        public MyViewHolder(View itemView) {
            super(itemView);
            // 初始化ViewHolder中的控件
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            nickname = (TextView) itemView.findViewById(R.id.nickname);
            datetime = (TextView) itemView.findViewById(R.id.datetime);
            content = (TextView) itemView.findViewById(R.id.content);
            empty_view = (TextView) itemView.findViewById(R.id.empty_view);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 加载ViewHolder的布局文件并创建ViewHolder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_item, parent, false);
        MyCommentAdapter.MyViewHolder vh = new MyCommentAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        // 获取数据并设置到ViewHolder中的控件上
        Glide.with(mContext).load(list.get(position).get("avatarurl").toString()).apply(RequestOptions.bitmapTransform(new RoundedCorners(100)).override(100, 100)).into(holder.avatar);
        holder.nickname.setText(list.get(position).get("nickname").toString());
        holder.datetime.setText(" 发布于 " + list.get(position).get("cdate").toString());
        holder.content.setText(list.get(position).get("content").toString());

    }

    @Override
    public int getItemCount() {
        // 返回数据项的数量
        return list.size();
    }


}
