package com.example.floatingisland.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.request.RequestOptions;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.WelcomeActivity;
import com.example.floatingisland.fragment.editInfoFragment;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.MyToast;
import kr.co.prnd.readmore.ReadMoreTextView;

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
        ReadMoreTextView content;
        TextView empty_view;
        ImageView delete;


        public MyViewHolder(View itemView) {
            super(itemView);
            // 初始化ViewHolder中的控件
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            nickname = (TextView) itemView.findViewById(R.id.nickname);
            datetime = (TextView) itemView.findViewById(R.id.datetime);
            content = (ReadMoreTextView) itemView.findViewById(R.id.content);
            empty_view = (TextView) itemView.findViewById(R.id.empty_view);
            delete = (ImageView) itemView.findViewById(R.id.delete);

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        // 获取数据并设置到ViewHolder中的控件上
        Glide.with(mContext).load(list.get(position).get("avatarurl").toString()).apply(RequestOptions.bitmapTransform(new RoundedCorners(100)).override(100, 100)).transform(new CircleTransformation()).into(holder.avatar);
        holder.nickname.setText(list.get(position).get("nickname").toString());
        holder.datetime.setText(" 发布于 " + list.get(position).get("cdate").toString());
        holder.content.setText(list.get(position).get("content").toString());

        //删除按钮检测
        HashMap<String, String> params = new HashMap<>();
        params.put("pid", list.get(position).get("pid").toString());
        params.put("faccount", list.get(position).get("uaccount").toString());
        params.put("uaccount", loginInfo);
        OkHttp.post(mContext, Constant.selectComment, params, new OkCallback<Result>() {
            @Override
            public void onResponse(Result response) {
                if(response.getMessage().equals("该账号发布")){
                    holder.delete.setVisibility(View.VISIBLE);
                }else if(response.getMessage().equals("非该账号发布")){
                    holder.delete.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });

        //删除评论
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("确定删除这条评论吗？")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击确定按钮后的操作
                                HashMap<String, String> params = new HashMap<>();
                                params.put("cid", list.get(position).get("cid").toString());
                                params.put("uaccount", loginInfo);
                                OkHttp.post(mContext, Constant.deleteComment, params, new OkCallback<Result>() {
                                    @Override
                                    public void onResponse(Result response) {
                                        MyToast.successBig(response.getMessage());

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





            }
        });

    }

    @Override
    public int getItemCount() {
        // 返回数据项的数量
        return list.size();
    }

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
