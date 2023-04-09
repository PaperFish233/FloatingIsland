package com.example.floatingisland.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.floatingisland.R;
import com.example.floatingisland.fragment.loginFragment;
import com.example.floatingisland.fragment.WelcomeFragment;
import com.example.floatingisland.fragment.protocolFragment;
import com.example.floatingisland.fragment.registerFragment;


public class WelcomeActivity extends AppCompatActivity {

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

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