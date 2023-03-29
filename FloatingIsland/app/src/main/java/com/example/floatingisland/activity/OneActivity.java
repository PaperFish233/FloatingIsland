package com.example.floatingisland.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.floatingisland.R;
import com.example.floatingisland.fragment.HomePageFragment;
import com.example.floatingisland.fragment.addpostsFragment;

public class OneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        //获取FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        //开始事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //将MyFragment添加到容器视图中
        addpostsFragment addpostsFragment = new addpostsFragment();
        fragmentTransaction.add(R.id.oneLayout, addpostsFragment);

        //提交事务
        fragmentTransaction.commit();




    }




}