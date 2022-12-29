package com.example.chapter09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.example.chapter09.receiver.StandardReceiver;

public class BroadStandardActivity extends AppCompatActivity implements View.OnClickListener {

    private StandardReceiver standardReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_standard);

//        了解标准广播
//        在代码中注册接收器，为动态注册
//                在AndroidManifest.xml中注册接收器，该方式为静态注册，8.0以后不再推荐
        findViewById(R.id.btn_send_standard).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // 发送广播、定义广播接收、开关广播接收器
//        Intent intent = new Intent("com.example.chapter09.standard");
        // 可以不用写死 硬编码
        Intent intent = new Intent(StandardReceiver.STANDARD_ACTION);
        sendBroadcast(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 注册广播，并启动,变成全局变量，便于在onStop中取消
        standardReceiver = new StandardReceiver();
        // 创建过滤器，只处理StandardReceiver.STANDARD_ACTION的广播
        IntentFilter intentFilter = new IntentFilter(StandardReceiver.STANDARD_ACTION);
//        注册接收器，接受广播
        registerReceiver(standardReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        注销 关闭广播
        unregisterReceiver(standardReceiver);
    }
}