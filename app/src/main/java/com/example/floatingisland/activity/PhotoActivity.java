package com.example.floatingisland.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.example.floatingisland.R;
import com.github.chrisbanes.photoview.PhotoView;

public class PhotoActivity extends AppCompatActivity {

    // 定义一个ImageView对象，用于后面更新图片的位置
    private PhotoView fullscreenPhotoview;

    private GestureDetector mGestureDetector;

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 将触摸事件传递给 GestureDetector 处理
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        // 创建 GestureDetector 对象并设置监听器
        mGestureDetector = new GestureDetector(this, new MyGestureListener());

        //设置状态栏为黑色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }

        fullscreenPhotoview = findViewById(R.id.fullscreen_photoview);

        Uri imageUri = getIntent().getData();
        if (imageUri != null) {
            Glide.with(this)
                    .load(imageUri)
                    .into(fullscreenPhotoview);
        }

    }
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (distanceY > 200) {
                finish(); // 关闭当前 Activity
                return true;
            }
            return false;
        }
    }
}

