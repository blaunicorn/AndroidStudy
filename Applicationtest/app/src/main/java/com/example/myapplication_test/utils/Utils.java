package com.example.myapplication_test.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {
    public  static  int dip2px(Context context, float dpValue) {
        // 获取当前手机的像素密度（1dp=？px）
       float scale =  context.getResources().getDisplayMetrics().density;
       return (int)(dpValue*scale + 0.5f);
    }

    public static  String getNowTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd HH:mm:ss.SSS"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//        SimpleDateFormat dateFormat = new SimpleDateFormat(("HH:mm:ss"));
        return  dateFormat.format(new Date());
    }
}
