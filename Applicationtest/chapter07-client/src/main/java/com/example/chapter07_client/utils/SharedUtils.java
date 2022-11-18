package com.example.chapter07_client.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtils {
    private static SharedUtils mUtils;
    //    SharedPreferences是Android平台上一个轻量级的存储类，用来保存应用的一些常用配置
    private SharedPreferences sharedPreferences;


    // 单例模式
    public static SharedUtils getInstance(Context ctx) {
        if (mUtils == null) {
            mUtils = new SharedUtils();
            mUtils.sharedPreferences = ctx.getSharedPreferences("shopping", Context.MODE_PRIVATE);
        }
        return mUtils;
    }

//    写
    public void writeBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

//    读
    public boolean readBoolean(String key, boolean defaultValue) {

        return sharedPreferences.getBoolean(key, defaultValue);

    }
}
