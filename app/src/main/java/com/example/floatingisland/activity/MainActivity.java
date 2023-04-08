package com.example.floatingisland.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.floatingisland.R;
import com.example.floatingisland.fragment.HomePageFragment;
import com.example.floatingisland.fragment.MineFragment;
import com.example.floatingisland.fragment.DiscoverFragment;
import com.example.floatingisland.fragment.registerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.jzvd.Jzvd;
import es.dmoral.toasty.MyToast;

public class MainActivity extends AppCompatActivity {

    // Fragment
    private HomePageFragment HomePageFragment = new HomePageFragment();
    private DiscoverFragment DiscoverFragment = new DiscoverFragment();
    private MineFragment MineFragment = new MineFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyToast.init(getApplication(),false,true);

        // 显示默认的Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, HomePageFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        // 取消导航栏蒙版
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, HomePageFragment).commit();
                        return true;
                    case R.id.discover:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, DiscoverFragment).commit();
                        return true;
                    case R.id.myself:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, MineFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

    private long firstPressedTime;
    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        } else {
            // System.currentTimeMillis() 当前系统的时间
            if (System.currentTimeMillis() - firstPressedTime < 2000) {
                super.onBackPressed();
            } else {
                MyToast.errorBig("再按一次退出应用");
                firstPressedTime = System.currentTimeMillis();
            }
        }
    }

}