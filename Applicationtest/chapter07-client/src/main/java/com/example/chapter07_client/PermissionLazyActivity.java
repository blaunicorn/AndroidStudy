package com.example.chapter07_client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.example.chapter07_client.utils.PermissionUtils;
import com.example.chapter07_client.utils.Utils;

public class PermissionLazyActivity extends AppCompatActivity implements View.OnClickListener {

    // 还需要在Manifest中增加权限
    // 通讯录的读写权限
    private static final String[] PERMISSIONS_CONTACTS =new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
    };
    private static  final int REQUEST_CODE_CONTACTS = 1;

    // 短信的读写权限
    private static  final  String[] PERMISSIONS_SMS = new String[] {
       Manifest.permission.SEND_SMS,
       Manifest.permission.RECEIVE_SMS
    };
    private static  final int REQUEST_CODE_SMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_lazy);

        findViewById(R.id.btn_contact).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_contact:
                // 懒人模式，用到时才检测是否有权限
//                1、调用ContextCompat的checkSelfPermission方法，检测app是否开启了指定权限；
//                2、调用ActivityCompat的requestPermission方法，命令系统自动弹出权限申请窗口；
//                3、重新活动页面的权限请求回调方法onRequestPermissionResult方法，在该方法内部处理用户的权限选择结果。
                PermissionUtils.checkPermission(this, PERMISSIONS_CONTACTS, REQUEST_CODE_CONTACTS);

                break;
            case R.id.btn_sms:
                 PermissionUtils.checkPermission(this, PERMISSIONS_SMS, REQUEST_CODE_SMS);
                break;
        }
    }

    // 用户权限回调的函数
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CONTACTS:
                if (PermissionUtils.checkGrant(grantResults)) {
                    Log.d("wcy", "onRequestPermissionsResult: success");
                } else {
                    Utils.show(this,"授权失败");
//                    如果授权失败，跳到设置界面
                    jumpToSettings();
                }
                break;
            case REQUEST_CODE_SMS:
                if (PermissionUtils.checkGrant(grantResults)) {
                    Log.d("wcy", "SMS onRequestPermissionsResult : success");
                } else {
                    Utils.show(this,"sms授权失败");
//                    如果授权失败，跳到设置界面
                    jumpToSettings();
                }
                break;
        }
    }

    // 转跳到应用设置界面
    private  void jumpToSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        // 传递当前应用的包名
        intent.setData(Uri.fromParts("package",getPackageName(),null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}