package com.example.floatingisland.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.floatingisland.R;
import com.example.floatingisland.fragment.noticeFragment;
import com.example.floatingisland.fragment.searchFragment;
import com.example.floatingisland.fragment.addPostsFragment;
import com.example.floatingisland.fragment.userInfoFragment;

public class OneActivity extends AppCompatActivity {

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        int jumpcode = getIntent().getIntExtra("jumpcode", 0);
        if (jumpcode == 1) {
            //获取FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //开始事务
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //将MyFragment添加到容器视图中
            addPostsFragment addpostsFragment = new addPostsFragment();
            fragmentTransaction.add(R.id.oneLayout, addpostsFragment);
            //提交事务
            fragmentTransaction.commit();
        }
        if (jumpcode == 2) {
            //获取FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //开始事务
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //将MyFragment添加到容器视图中
            searchFragment searchFragment = new searchFragment();
            fragmentTransaction.add(R.id.oneLayout, searchFragment);
            //提交事务
            fragmentTransaction.commit();
        }
        if (jumpcode == 3) {
            //获取FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //开始事务
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //将MyFragment添加到容器视图中
            userInfoFragment userInfoFragment = new userInfoFragment();
            fragmentTransaction.add(R.id.oneLayout, userInfoFragment);
            //提交事务
            fragmentTransaction.commit();
        }
        if (jumpcode == 4) {
            //接收传递值
            int nid = getIntent().getIntExtra("nid",0);

            //发送传递值
            Bundle bundle = new Bundle();
            bundle.putInt("nid", nid);
            // 创建MyBottomSheetDialogFragment的实例
            noticeFragment noticeFragment = new noticeFragment();
            noticeFragment.setArguments(bundle);

            //获取FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //开始事务
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //将MyFragment添加到容器视图中
            fragmentTransaction.add(R.id.oneLayout, noticeFragment);
            //提交事务
            fragmentTransaction.commit();
        }

    }

}