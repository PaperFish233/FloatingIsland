package com.example.floatingisland.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.floatingisland.R;
import com.example.floatingisland.fragment.addPostsFragment;
import com.example.floatingisland.fragment.collectionPostsFragment;
import com.example.floatingisland.fragment.minePostsFragment;

public class ThereActivity extends AppCompatActivity {

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
            minePostsFragment minepostsFragment = new minePostsFragment();
            fragmentTransaction.add(R.id.thereLayout, minepostsFragment);
            //提交事务
            fragmentTransaction.commit();

        } if(jumpcode == 2){
            //获取FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //开始事务
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //将MyFragment添加到容器视图中
            collectionPostsFragment collectionpostsFragment = new collectionPostsFragment();
            fragmentTransaction.add(R.id.thereLayout, collectionpostsFragment);
            //提交事务
            fragmentTransaction.commit();
        }
    }
}