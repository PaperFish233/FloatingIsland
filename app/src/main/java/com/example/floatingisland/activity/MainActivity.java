package com.example.floatingisland.activity;

import static com.example.floatingisland.utils.PermissionHelper.requestCameraPermission;
import static com.example.floatingisland.utils.PermissionHelper.requestReanAndWritePermission;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.floatingisland.R;
import com.example.floatingisland.entity.Users;
import com.example.floatingisland.entity.Version;
import com.example.floatingisland.fragment.DiscoverFragment;
import com.example.floatingisland.fragment.HomePageFragment;
import com.example.floatingisland.fragment.MineFragment;
import com.example.floatingisland.fragment.mineInfoFragment;
import com.example.floatingisland.fragment.settingFragment;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.List;

import cn.jzvd.Jzvd;
import constant.UiType;
import es.dmoral.toasty.MyToast;
import listener.Md5CheckResultListener;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

public class MainActivity extends AppCompatActivity {

    // Fragment
    private HomePageFragment HomePageFragment = new HomePageFragment();
    private DiscoverFragment DiscoverFragment = new DiscoverFragment();
    private MineFragment MineFragment = new MineFragment();

    //APK的地址
    private String apkUrl = "";
    //更新弹窗标题
    private String updateTitle = "";
    //更新弹窗简介
    private String updateContent = "";

    //当前版本号
    private String thisVersion = "";

    public void UpdateApp(){
        //更新配置信息
        UpdateConfig updateConfig = new UpdateConfig();
        //检查Wifi状态
        updateConfig.setCheckWifi(true);
        //开启MD5校验
        updateConfig.setNeedCheckMd5(true);
        //开启下载进度条
        updateConfig.setAlwaysShowDownLoadDialog(true);
        //关闭通知栏进度条
        updateConfig.setShowNotification(false);
        //关闭下载提示框
        updateConfig.setShowDownloadingToast(false);

        //UI配置信息
        UiConfig uiConfig = new UiConfig();
        uiConfig.setUiType(UiType.PLENTIFUL);

        //功能Api
        UpdateAppUtils
                .getInstance()
                .apkUrl(apkUrl)
                .updateTitle(updateTitle)
                .updateContent(updateContent)
                .uiConfig(uiConfig)
                .updateConfig(updateConfig)
                .setMd5CheckResultListener(new Md5CheckResultListener() {
                    @Override
                    public void onResult(boolean result) {
                        if(result == false) {
                            MyToast.successBig("Md5校验未通过，请晚些再更新！");
                        }
                    }
                })
                .update();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //动态获取权限
//        requestCameraPermission(MainActivity.this);
        requestReanAndWritePermission(MainActivity.this);

        MyToast.init(getApplication(),false,true);

        UpdateAppUtils.init(this);

        PackageManager packageManager = this.getPackageManager();
        String packageName = this.getPackageName();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        thisVersion = String.valueOf(packageInfo.versionCode)+"."+String.valueOf(packageInfo.versionName);

        //获取版本信息
        OkHttp.post(this, Constant.getVersion, null, new OkCallback<Result<List<Version>>>() {
            @Override
            public void onResponse(Result<List<Version>> response) {
                String Vnumber="";
                String Vupdatetittle="";
                String Vcontent="";
                String Vapkurl="";
                for (Version version : response.getData()) {
                    Vnumber = version.getVnumber();
                    Vupdatetittle = version.getVupdatetitle();
                    Vcontent = version.getVcontent();
                    Vapkurl = version.getVapkurl();
                }
                if(!thisVersion.equals(Vnumber)){
                    apkUrl = Vapkurl;
                    updateTitle = Vupdatetittle;
                    updateContent = Vcontent;
                    UpdateApp();
                }

            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });


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