package com.example.floatingisland.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.example.floatingisland.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CommentBottomDialog extends BottomSheetDialogFragment {
    
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

        // 设置 dialog 布局
        initView(dialog);
        return dialog;

    }

    private void initView(BottomSheetDialog dialog) {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_comment, null);
        dialog.setContentView(root);
        //设置高度
        ViewGroup.LayoutParams params = root.getLayoutParams();
        params.height = (int) (0.9 *
                getResources().getDisplayMetrics().heightPixels);
        root.setLayoutParams(params);

        // 初始化 dialog 布局里的控件
        ImageView iv_close = root.findViewById(R.id.iv_close);

        // 点击关闭 dialog
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
