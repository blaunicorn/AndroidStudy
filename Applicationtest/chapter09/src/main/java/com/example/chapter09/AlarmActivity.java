package com.example.chapter09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.example.chapter09.receiver.AlarmReceiver;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {


    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        findViewById(R.id.btn_alarm).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //  发送闹钟定时请求
        alarmReceiver.sendAlarm();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        注册
//        AlarmReceiver alarmReceiver = new AlarmReceiver(this);
        alarmReceiver = new AlarmReceiver(getApplicationContext());
        IntentFilter intentFilter = new IntentFilter(AlarmReceiver.ALARM_ACTION);
        registerReceiver(alarmReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(alarmReceiver);
    }
}