package com.example.chapter09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.chapter09.receiver.TimerReceiver;

public class SystemMinuteActivity extends AppCompatActivity {

    private TimerReceiver timerReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_minute);

//        接收系统广播的例子，1、接收分钟到达广播 2、接收网络变更广播；3、接收定时器管理


    }

    @Override
    protected void onStart() {
        super.onStart();
//        创建分钟变更的intent 广播接收器
        timerReceiver = new TimerReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(timerReceiver,filter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(timerReceiver);
    }
}