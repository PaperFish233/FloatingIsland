package com.example.floatingisland.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.floatingisland.R;
import com.example.floatingisland.fragment.loginFragment;
import com.example.floatingisland.fragment.WelcomeFragment;
import com.example.floatingisland.fragment.protocolFragment;
import com.example.floatingisland.fragment.registerFragment;


public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

            //获取FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //开始事务
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //将MyFragment添加到容器视图中
            WelcomeFragment welcomeFragment = new WelcomeFragment();
            fragmentTransaction.add(R.id.welcomeLayout, welcomeFragment);
            //提交事务
            fragmentTransaction.commit();


    }
}