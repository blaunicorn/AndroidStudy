package com.example.myapplication_test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerActivity extends AppCompatActivity {

    private TextView tv_time;
    private TimePicker timepicker2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        TimePicker timepicker = (TimePicker) findViewById(R.id.tp_time);
        timepicker2 = (TimePicker) findViewById(R.id.tp_time2);

//        通过 setIs24HourView(true) 方法改成 24 小时制
        timepicker.setIs24HourView(true);
        timepicker2.setIs24HourView(true);
        tv_time = findViewById(R.id.tv_time);

        findViewById(R.id.btn_ok).setOnClickListener(this::customClickFn);
        findViewById(R.id.btn_dialog).setOnClickListener(this::customClickFn);
    }

    private void customClickFn(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                String desc = String.format("您选择是时间是%d时%d分",timepicker2.getHour(),timepicker2.getMinute());
                tv_time.setText(desc);
                break;
            case R.id.btn_dialog:
                // 获取日历的一个实例，获取当前的时分秒(采用单例模式)
                Calendar calendar =  Calendar.getInstance();
                // 构建时间对话框，
                TimePickerDialog dialog = new TimePickerDialog((Context) this, android.R.style.Theme_Holo_Light_Dialog, (TimePickerDialog.OnTimeSetListener) this::customTimeSetFn,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
                dialog.setTitle("pick");
                      dialog.show();
                break;
        }
    }

    private void customTimeSetFn(TimePicker timePicker, int hourOfDay, int minute) {
        String desc = String.format("您选择是时间是%d时%d分",hourOfDay,minute);
        tv_time.setText(desc);
    }
}