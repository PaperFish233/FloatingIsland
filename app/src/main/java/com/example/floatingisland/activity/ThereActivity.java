package com.example.floatingisland.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.floatingisland.R;
import com.example.floatingisland.fragment.mineCollectionPostsFragment;
import com.example.floatingisland.fragment.mineInfoFragment;
import com.example.floatingisland.fragment.minePostsFragment;
import com.example.floatingisland.fragment.settingFragment;

import cn.jzvd.Jzvd;

public class ThereActivity extends AppCompatActivity {

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        } else {
           super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_there);


        int jumpcode = getIntent().getIntExtra("jumpcode", 0);
        if (jumpcode == 1) {
            //获取FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //开始事务
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //将MyFragment添加到容器视图中
            mineInfoFragment mineInfoFragment = new mineInfoFragment();
            fragmentTransaction.add(R.id.thereLayout, mineInfoFragment);
            //提交事务
            fragmentTransaction.commit();

        }
        if (jumpcode == 2) {
            //获取FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //开始事务
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //将MyFragment添加到容器视图中
            settingFragment settingFragment = new settingFragment();
            fragmentTransaction.add(R.id.thereLayout, settingFragment);
            //提交事务
            fragmentTransaction.commit();
        }
        if (jumpcode == 3) {
            //获取FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //开始事务
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //将MyFragment添加到容器视图中
            mineCollectionPostsFragment mineCollectionPostsFragment = new mineCollectionPostsFragment();
            fragmentTransaction.add(R.id.thereLayout, mineCollectionPostsFragment);
            //提交事务
            fragmentTransaction.commit();
        }
    }
}