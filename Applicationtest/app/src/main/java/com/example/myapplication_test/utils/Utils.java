package com.example.myapplication_test.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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

    // 隐藏键盘
   public  static  void hideOneInputMethod(Activity activity, View view) {
        // 从系统服务中获取输入法管理器
       InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
       // 关闭屏幕上的输入法软键盘
       imm.hideSoftInputFromWindow(view.getWindowToken(),0);
   }
}
