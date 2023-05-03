package com.example.floatingisland.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.floatingisland.R;
import com.example.floatingisland.fragment.aboutFragment;
import com.example.floatingisland.fragment.editPostsFragment;
import com.example.floatingisland.fragment.focusUserFragment;
import com.example.floatingisland.fragment.mineCollectionPostsFragment;
import com.example.floatingisland.fragment.mineInfoFragment;
import com.example.floatingisland.fragment.protocolFragment;
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
        if (jumpcode == 4) {
            //接收传递值
            String lastpid = String.valueOf(getIntent().getIntExtra("lastpid",0));
            String lastcontent = getIntent().getStringExtra("lastcontent");
            String lastimageurl = getIntent().getStringExtra("lastimageurl");
            System.out.println(lastpid+lastcontent+lastimageurl);

            //发送传递值
            Bundle bundle = new Bundle();
            bundle.putString("lastpid", lastpid);
            bundle.putString("lastcontent", lastcontent);
            bundle.putString("lastimageurl", lastimageurl);
            // 创建MyBottomSheetDialogFragment的实例
            editPostsFragment editPostsFragment = new editPostsFragment();
            editPostsFragment.setArguments(bundle);

            //获取FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //开始事务
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //将MyFragment添加到容器视图中
            fragmentTransaction.add(R.id.thereLayout, editPostsFragment);
            //提交事务
            fragmentTransaction.commit();
        }
        if (jumpcode == 5) {
            //获取FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //开始事务
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //将MyFragment添加到容器视图中
            protocolFragment protocolFragment = new protocolFragment();
            fragmentTransaction.add(R.id.thereLayout, protocolFragment);
            //提交事务
            fragmentTransaction.commit();

        }
        if (jumpcode == 6) {
            //获取FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //开始事务
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //将MyFragment添加到容器视图中
            aboutFragment aboutFragment = new aboutFragment();
            fragmentTransaction.add(R.id.thereLayout, aboutFragment);
            //提交事务
            fragmentTransaction.commit();

        }
        if (jumpcode == 7) {
            //接收传递值
            Boolean isFacusUser = getIntent().getBooleanExtra("isFacusUser",true);

            //发送传递值
            Bundle bundle = new Bundle();
            bundle.putBoolean("isFacusUser", isFacusUser);
            // 创建focusUserFragment的实例
            focusUserFragment focusUserFragment = new focusUserFragment();
            focusUserFragment.setArguments(bundle);

            //获取FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //开始事务
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //将MyFragment添加到容器视图中
            fragmentTransaction.add(R.id.thereLayout, focusUserFragment);
            //提交事务
            fragmentTransaction.commit();

        }
    }

}