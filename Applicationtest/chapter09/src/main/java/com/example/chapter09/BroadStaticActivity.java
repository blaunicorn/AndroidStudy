package com.example.chapter09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BroadStaticActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_static);

        findViewById(R.id.btn_send_shock).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent("com.example.chapter09.shock");
//     8.0以后版本，为了让应用能够继续接收静态广播，需要给静态注册的广播指定包名
        String fullName = "com.example.chapter09.receiver";
//        发送静态广播之时，需要通过指定接收器的完整路径
        ComponentName componentName = new ComponentName(this,fullName);
        intent.setComponent(componentName);
        sendBroadcast(intent);
    }
}