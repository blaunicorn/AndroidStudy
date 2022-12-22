package com.example.chapter07_client;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.chapter07_client.utils.PermissionUtils;
import com.example.chapter07_client.utils.Utils;

import java.io.File;
import java.security.Permission;

public class ProviderAppActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String[] PERMISSIONS = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_app);
        // 程序目的：实现点击按钮，安装apk

        Log.d("wcy", "onCreate: ");

        // 设置点击事件
        findViewById(R.id.btn_install).setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && PermissionUtils.checkGrant(grantResults)) {
            installApk();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_install:
//                安卓11后还需要新的安装权限---扩展存储管理权限，先判断是否赋予了这个权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Log.d("wcy", "Android:" + Build.VERSION_CODES.R);
                    checkAndInstall();
                } else {
                    if(PermissionUtils.checkPermission(this,PERMISSIONS,PERMISSION_REQUEST_CODE)) {
                        installApk();
                    }
                }

                break;
        }
    }

    private void checkAndInstall() {
//        如果没有权限 跳转到设置页面
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent);
            } else {
                if(PermissionUtils.checkPermission(this,PERMISSIONS,PERMISSION_REQUEST_CODE)) {
                    installApk();
                }

            }
        }
    }

    //    安装apk的默认文件
//    /storage/emulated/0/Download/chapter06-debug.apk
    private void installApk() {
        String apkPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/chapter06-debug.apk";
        Log.d("wcy", "apkPath:" + apkPath);
//         先检测下文件是否完整
        // 获取包管理器
        PackageManager packageManager = getPackageManager();
        //  通过包管理器获取 已知api的activity信息
        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (packageArchiveInfo == null) {
            // 如果activity为空，就说明包不完整
            Utils.show(this, "安装包已损坏");
            return;
        }
        // 交给系统去安装
        Uri uri = Uri.parse(apkPath);
        // 7.0 之后，需要通过FileProvider去安装
        //  兼容Android7.0 把访问文件的Uri方法改成FileProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 通过FileProvider 获得文件的Uri访问方式
            uri = FileProvider.getUriForFile(this, "Download", new File(apkPath));
            Log.d("wcy", "new uri:" + uri.toString());
        }
        // 跳转到系统的安装程序, 并请求授权，并设置Uri的数据类型是APK文件，然后启动系统自带的安装程序
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(intent);
//        安卓8.0之前，需要READ_EXTERNAL_STORAGE权限
//          <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
//        安卓8.0后还需要新的安装权限 REQUEST_INSTALL_PACKAGES
//          <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"></uses-permission>
        // 安卓11后还需要新的安装权限,新的检测方式---扩展存储管理权限
//          <uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE"></uses-permission>
    }
}