package com.example.floatingisland.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.floatingisland.R;
import com.example.floatingisland.entity.PostsComment;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.MyCommentAdapter;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.MyToast;

public class CommentBottomDialog extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;

    //将数据封装成数据源
    List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();

    //获取评论数据
    private void getCommentDate(View view,int pid){

        HashMap<String, String> params1 = new HashMap<>();
        params1.put("pid", String.valueOf(pid));
        OkHttp.post(getContext(), Constant.getCommentPosts, params1, new OkCallback<Result<List<PostsComment>>>() {
            @Override
            public void onResponse(Result<List<PostsComment>> response) {
                for (PostsComment datum : response.getData()) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("avatarurl", datum.getAvatarurl());
                    map.put("nickname", datum.getNickname());
                    map.put("cdate", datum.getCdate());
                    map.put("content", datum.getContent());
                    list.add(map);
                }
                // 绑定数据适配器MyAdapter
                MyCommentAdapter MyCommentAdapter = new MyCommentAdapter(getContext(), list);
                recyclerView.setAdapter(MyCommentAdapter);

            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });

    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        if (getActivity() == null) return super.onCreateDialog(savedInstanceState);
        // 第二个参数是设置 dialog 的背景样式
        BottomSheetDialog dialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetStyle);
        // 这个是设置 dialog 弹出动画
        Window window = dialog.getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.BottomSheetStyle);
        }

        View root = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_comment, null);
        dialog.setContentView(root);
        //设置高度
        ViewGroup.LayoutParams params = root.getLayoutParams();
        params.height = (int) (0.9 *
                getResources().getDisplayMetrics().heightPixels);
        root.setLayoutParams(params);

        // 初始化 dialog 布局里的控件
        ImageView iv_close = root.findViewById(R.id.iv_close);
        MaterialEditText commentinfo = root.findViewById(R.id.commentinfo);
        Button addcomment = root.findViewById(R.id.addcomment);
        ImageView isempty= root.findViewById(R.id.isempty);

        // 点击关闭 dialog
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //接收pid
        Bundle bundle = getArguments();
        int pid = 0;
        if (bundle != null) {
            pid = bundle.getInt("pid");
        }

        //获取账号
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        //获取评论数据
        HashMap<String, String> params1 = new HashMap<>();
        params1.put("pid", String.valueOf(pid));
        OkHttp.post(getContext(), Constant.getCommentPosts, params1, new OkCallback<Result<List<PostsComment>>>() {
            @Override
            public void onResponse(Result<List<PostsComment>> response) {
                for (PostsComment datum : response.getData()) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("avatarurl", datum.getAvatarurl());
                    map.put("nickname", datum.getNickname());
                    map.put("cdate", datum.getCdate());
                    map.put("content", datum.getContent());
                    list.add(map);
                }
                // 获取 RecyclerView 控件
                recyclerView = root.findViewById(R.id.recycler_comment_view);
                // 创建 LinearLayoutManager 对象，设置为垂直方向
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                // 绑定 LayoutManager
                recyclerView.setLayoutManager(layoutManager);

                //无评论页面显示标语
                if(list.isEmpty()){
                    isempty.setVisibility(View.VISIBLE);
                }else{
                    isempty.setVisibility(View.GONE);
                    // 绑定数据适配器MyAdapter
                    MyCommentAdapter MyCommentAdapter = new MyCommentAdapter(getContext(), list);
                    recyclerView.setAdapter(MyCommentAdapter);
                }

                // 创建 SpaceItemDecoration 实例，设置10dp的间距
                SpaceItemDecoration decoration = new SpaceItemDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                // 添加
                recyclerView.addItemDecoration(decoration);

            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });

        //发布评论
        int finalPid = pid;
        addcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(commentinfo.getText().toString().equals("")){
                    MyToast.errorBig("评论内容不能为空！");
                }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("确定发布这条评论吗？")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击确定按钮后的操作
                                HashMap<String, String> params2 = new HashMap<>();
                                params2.put("pid", String.valueOf(finalPid));
                                params2.put("uaccount", loginInfo);
                                params2.put("comment", commentinfo.getText().toString());
                                OkHttp.post(getContext(), Constant.insertCommentPosts, params2, new OkCallback<Result>() {
                                    @Override
                                    public void onResponse(Result response) {
                                        MyToast.successBig(response.getMessage());
                                        commentinfo.setText("");
                                        isempty.setVisibility(View.GONE);
                                        list.clear();
                                        getCommentDate(root,finalPid);
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
            }
        });

        return dialog;

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
