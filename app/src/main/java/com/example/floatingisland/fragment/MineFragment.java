package com.example.floatingisland.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.example.floatingisland.activity.ThereActivity;
import com.example.floatingisland.entity.Users;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.MyToast;

public class MineFragment extends Fragment {

    private View MineFragment;

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        MineFragment = inflater.inflate(R.layout.fragment_mine, container, false);

        SuperTextView nickname = MineFragment.findViewById(R.id.nickname);
        ImageView setting = MineFragment.findViewById(R.id.setting);
        GridView gridview = MineFragment.findViewById(R.id.gridview);
        SuperTextView postsnum = MineFragment.findViewById(R.id.postsnum);
        SuperTextView ffocusnum = MineFragment.findViewById(R.id.ffocusnum);
        SuperTextView ufocusnum = MineFragment.findViewById(R.id.ufocusnum);

        gridview.setAdapter(new MyGridViewAdapter());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        //获取登录用户信息
        HashMap<String, String> params = new HashMap<>();
        params.put("uaccount", loginInfo);
        OkHttp.post(getContext(), Constant.getUsers, params, new OkCallback<Result<List<Users>>>() {
            @Override
            public void onResponse(Result<List<Users>> response) {
                String Uavatarurl="";
                String Uaccount="";
                String Unickname="";
                String Upostsnum="";
                String Uffocusnum="";
                String Uufocusnum="";
                for (Users users : response.getData()) {
                    Uavatarurl = users.getUavatarurl();
                    Uaccount = users.getUaccount();
                    Unickname = users.getUnickname();
                    Upostsnum = String.valueOf(users.getPostsnum());
                    Uffocusnum = String.valueOf(users.getFfocusnum());
                    Uufocusnum = String.valueOf(users.getUfocusnum());
                }

                Glide.with(getContext())
                        .load(Uavatarurl)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(100))
                                .override(128, 128))
                        .transform(new CircleTransformation())
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                nickname.setLeftIcon(resource);
                            }
                        });

                nickname.setLeftTopString(Unickname);
                postsnum.setCenterTopString(Upostsnum);
                ffocusnum.setCenterTopString(Uffocusnum);
                ufocusnum.setCenterTopString(Uufocusnum);

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("lastuserInfo", Context.MODE_PRIVATE);
                String lastAccount = sharedPreferences.getString("lastaccount", null);
                String lastAvatarUrl = sharedPreferences.getString("lastavatarurl", null);
                if (lastAccount == null && lastAvatarUrl == null) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("lastaccount", Uaccount);
                    editor.putString("lastavatarurl", Uavatarurl);
                    editor.apply();
                }
            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThereActivity.class);
                intent.putExtra("jumpcode", 2);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
            }
        });

        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThereActivity.class);
                intent.putExtra("jumpcode", 1);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
            }
        });

        postsnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThereActivity.class);
                intent.putExtra("jumpcode", 1);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
            }
        });

        ffocusnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThereActivity.class);
                intent.putExtra("jumpcode", 7);
                intent.putExtra("isFacusUser", true);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
            }
        });

        ufocusnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThereActivity.class);
                intent.putExtra("jumpcode", 7);
                intent.putExtra("isFacusUser", false);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
            }
        });


        return MineFragment;
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

    private class MyGridViewAdapter extends BaseAdapter {

        // 记录每个 item 的可点状态
        private boolean[] mClickableArray = {true, false, false, false, false, false, false, false};

        private final List<Item> mData = Arrays.asList(
                new Item(R.mipmap.collection1, "我的收藏"),
                new Item(0, ""),
                new Item(0, ""),
                new Item(0, ""),
                new Item(0, ""),
                new Item(0, ""),
                new Item(0, ""),
                new Item(0, "")

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
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.grid_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Item item = mData.get(position);
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
                                Intent intent = new Intent(getContext(), ThereActivity.class);
                                intent.putExtra("jumpcode", 3);
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                            case 4:
                                break;
                            case 5:
                                break;
                            case 6:
                                break;
                            case 7:
                                break;
                            case 8:
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