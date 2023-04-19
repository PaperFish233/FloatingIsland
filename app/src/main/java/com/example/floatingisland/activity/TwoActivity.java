package com.example.floatingisland.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.floatingisland.R;
import com.example.floatingisland.fragment.DiscoverTPFragment;
import com.example.floatingisland.fragment.chooseTopicFragment;
import com.example.floatingisland.fragment.editPostsFragment;
import com.example.floatingisland.fragment.mineInfoFragment;
import com.example.floatingisland.fragment.topicPostsFragment;

import cn.jzvd.Jzvd;

public class TwoActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_two);

        int jumpcode = getIntent().getIntExtra("jumpcode", 0);
        if (jumpcode == 1) {
            //接收传递值
            String tid = getIntent().getStringExtra("tid");

            //发送传递值
            Bundle bundle = new Bundle();
            bundle.putString("tid", tid);
            // 创建topicPostsFragment的实例
            topicPostsFragment topicPostsFragment = new topicPostsFragment();
            topicPostsFragment.setArguments(bundle);

            //获取FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //开始事务
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //将MyFragment添加到容器视图中
            fragmentTransaction.add(R.id.twoLayout, topicPostsFragment);
            //提交事务
            fragmentTransaction.commit();

        }
        if (jumpcode == 2) {
            //获取FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //开始事务
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //将MyFragment添加到容器视图中
            chooseTopicFragment chooseTopicFragment = new chooseTopicFragment();
            fragmentTransaction.add(R.id.twoLayout, chooseTopicFragment);
            //提交事务
            fragmentTransaction.commit();

        }



    }
}