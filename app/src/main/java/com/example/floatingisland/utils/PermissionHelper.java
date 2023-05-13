package com.example.floatingisland.utils;

import android.Manifest;
import android.app.Activity;

import androidx.annotation.NonNull;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class PermissionHelper implements EasyPermissions.PermissionCallbacks {

    private static final int PERMISSION_ReanAndWrite_REQUEST_CODE = 100;
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // 将结果委托给 EasyPermissions 处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PERMISSION_ReanAndWrite_REQUEST_CODE) {
            // 执行需要读写权限的操作

        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        // 权限已被拒绝
        if (requestCode == PERMISSION_ReanAndWrite_REQUEST_CODE) {
            // 显示一条消息，提示用户为该应用程序启用所需的权限
        }
    }

    public static void requestReanAndWritePermission(Activity activity) {

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            // 当前已有所需权限
            return;
        }
        // 显示申请权限的提示
        EasyPermissions.requestPermissions(activity,"为了能成功保存图片以及上传和下载资源，应用需要请求权限以访问您的文件。",PERMISSION_ReanAndWrite_REQUEST_CODE,perms);
    }

}
