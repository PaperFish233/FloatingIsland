package com.example.floatingisland.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.floatingisland.R;
import com.example.floatingisland.fragment.mineCollectionPostsFragment;
import com.example.floatingisland.fragment.minePostsFragment;

import cn.jzvd.Jzvd;
import es.dmoral.toasty.MyToast;

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
            mineCollectionPostsFragment collectionpostsFragment = new mineCollectionPostsFragment();
            fragmentTransaction.add(R.id.thereLayout, collectionpostsFragment);
            //提交事务
            fragmentTransaction.commit();
        }
    }
}