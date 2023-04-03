package com.example.floatingisland.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Application;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.floatingisland.R;
import com.example.floatingisland.fragment.CommentBottomDialog;
import com.example.floatingisland.fragment.HomePageFragment;
import com.example.floatingisland.fragment.MineFragment;
import com.example.floatingisland.fragment.TopicFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import es.dmoral.toasty.MyToast;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;// 视图翻页工具
    private BottomNavigationView navigationView;// 底部导航栏

    // Fragment
    private HomePageFragment HomePageFragment = new HomePageFragment();
    private TopicFragment TopicFragment = new TopicFragment();
    private MineFragment MineFragment = new MineFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyToast.init(getApplication(),false,true);

        initView();
    }

    /**
     * 初始化视图
     * */
    public void initView(){

        viewPager = findViewById(R.id.viewPager);
        // 监听viewPager页面变化事件
        viewPager.addOnPageChangeListener(viewPagerListener);
        // 获取Fragment管理对象
        FragmentManager fragmentManager = getSupportFragmentManager();
        // FragmentPagerAdapter 来处理多个 Fragment 页面
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return HomePageFragment;
                    case 1:
                        return TopicFragment;
                    case 2:
                        return MineFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        };

        // viewPager 设置 adapter
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(3); //预加载个数

        // 底部导航栏
        navigationView = findViewById(R.id.nav_view);
        // navigationView点击事件
        navigationView.setOnNavigationItemSelectedListener(navigationViewListener);
        // 取消导航栏蒙版
        navigationView.setItemIconTintList(null);


    }

    /**
     * 两次返回退出程序
     */
    private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < 2000) {
                System.exit(0);
            } else {
                MyToast.errorBig("再按一次退出应用");
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 监听ViewPager页面变化事件
     * */
    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            navigationView.getMenu().getItem(position).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };


    /**
     * 监听BottomNavigationView点击事件
     * */
    BottomNavigationView.OnNavigationItemSelectedListener navigationViewListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            // menu文件夹中bottom_nav_menu.xml里加的android:orderInCategory属性就是下面item.getOrder()取的值
            viewPager.setCurrentItem(menuItem.getOrder());
            return true;
        }
    };

}