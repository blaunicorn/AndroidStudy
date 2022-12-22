package com.example.chapter02.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.chapter02.adapter.MobilePagerAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class utils {


    /**
     * 获取id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int id(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "id", context.getPackageName());
    }

    /**
     * 获取anim类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int anim(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "anim", context.getPackageName());
    }

    /**
     * 获取layout类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int layout(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "layout", context.getPackageName());
    }

    /**
     * 获取drawable类型资源id
     *
     *
     * @param context
     * @param resName 资源名称
     * @return 资源id
     */
    public static int drawable(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "drawable", context.getPackageName());
    }

    /**
     * 获取string类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int string(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "string", context.getPackageName());
    }

    /**
     * 获取raw类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int raw(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "raw", context.getPackageName());
    }

    /**
     * 获取style类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int style(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "style", context.getPackageName());
    }

    // 加载网络图片
    public static Bitmap getBitmap(String path) throws IOException {

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200){
            InputStream inputStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        return null;
    }
}
