package com.example.chapter02.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public  static String getNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return  simpleDateFormat.format(new Date());
    }

    // 显示当前日期
    public  static  String getDate(Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  simpleDateFormat.format(date);
    }

    public static String getMonth(Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        return  simpleDateFormat.format(date);
    }
}
