package com.example.floatingisland.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.floatingisland.R;

public class aboutFragment extends Fragment {

    private View aboutFragment;

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        aboutFragment = inflater.inflate(R.layout.fragment_about, container, false);

        Toolbar toolbar = aboutFragment.findViewById(R.id.Toolbar_about);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

        TextView versionCodeName = aboutFragment.findViewById(R.id.versionCodeName);

        PackageManager packageManager = getContext().getPackageManager();
        String packageName = getContext().getPackageName();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        versionCodeName.setText(String.valueOf(packageInfo.versionCode)+"."+String.valueOf(packageInfo.versionName)+"（浮游岛）");

        return aboutFragment;
    }

}