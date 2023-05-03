package com.example.floatingisland.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.floatingisland.R;
import com.example.floatingisland.entity.Version;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;

import java.util.List;

import constant.UiType;
import es.dmoral.toasty.MyToast;
import listener.Md5CheckResultListener;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

public class aboutFragment extends Fragment {

    private View aboutFragment;

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    //APK的地址
    private String apkUrl = "";
    //更新弹窗标题
    private String updateTitle = "";
    //更新弹窗简介
    private String updateContent = "";

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        aboutFragment = inflater.inflate(R.layout.fragment_about, container, false);

        Toolbar toolbar = aboutFragment.findViewById(R.id.Toolbar_about);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

        TextView versionCodeName = aboutFragment.findViewById(R.id.versionCodeName);
        TextView update = aboutFragment.findViewById(R.id.update);

        PackageManager packageManager = getContext().getPackageManager();
        String packageName = getContext().getPackageName();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        versionCodeName.setText(String.valueOf(packageInfo.versionCode)+"."+String.valueOf(packageInfo.versionName)+"（浮游岛）");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取版本信息
                OkHttp.post(getContext(), Constant.getVersion, null, new OkCallback<Result<List<Version>>>() {
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
                        if(!versionCodeName.getText().toString().equals(Vnumber)){
                            apkUrl = Vapkurl;
                            updateTitle = Vupdatetittle;
                            updateContent = Vcontent;
                            UpdateApp();
                        }else{
                         MyToast.successBig("当前已是最新版！");
                        }
                    }

                    @Override
                    public void onFailure(String state, String msg) {
                        MyToast.errorBig("连接服务器超时！");
                    }
                });
            }
        });

        return aboutFragment;
    }

}