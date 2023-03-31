package com.example.floatingisland.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.floatingisland.R;
import com.example.floatingisland.fragment.CommentBottomDialog;
import com.example.floatingisland.fragment.addPostsFragment;
import com.example.floatingisland.fragment.collectionPostsFragment;
import com.example.floatingisland.fragment.minePostsFragment;

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
        addPostsFragment addpostsFragment = new addPostsFragment();
        fragmentTransaction.add(R.id.oneLayout, addpostsFragment);
        CommentBottomDialog commentBottomDialog = new CommentBottomDialog();
        fragmentTransaction.add(R.id.oneLayout, commentBottomDialog);
        //提交事务
        fragmentTransaction.commit();






    }




}