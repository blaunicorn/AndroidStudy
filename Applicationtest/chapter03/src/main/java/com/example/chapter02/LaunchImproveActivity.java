package com.example.chapter02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.chapter02.adapter.LaunchImproveAdapter;
import com.example.chapter02.adapter.LaunchImproveFromUrlAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class LaunchImproveActivity extends AppCompatActivity {

    // 本地图片
    private int[] launchImageArray = {R.drawable.c01,
            R.drawable.c02,
            R.drawable.c03,
            R.drawable.c04,
            R.drawable.c05,
            R.drawable.c06};
    // 网络图片
    private String[] urlImages = new String[]{"https://t7.baidu.com/it/u=825057118,3516313570&fm=193&f=GIF",
            "https://t7.baidu.com/it/u=4198287529,2774471735&fm=193&f=GIF",
            "https://t7.baidu.com/it/u=1956604245,3662848045&fm=193&f=GIF",
            "https://t7.baidu.com/it/u=2529476510,3041785782&fm=193&f=GIF",
            "https://t7.baidu.com/it/u=727460147,2222092211&fm=193&f=GIF",
            "https://t7.baidu.com/it/u=2511982910,2454873241&fm=193&f=GIF"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_improve);

        //  引导页开发
//        先判断是否第一次启动app，如果是，则进入引导页(左右滑动切换查看，滑动到最后一页点击按钮进入首页)。
//        如果不是，则直接进入首页

        ViewPager vp_launch = findViewById(R.id.vp_launch);

//        加载本地图片
 //       LaunchImproveAdapter adapter = new LaunchImproveAdapter(getSupportFragmentManager(),launchImageArray);

//        加载网络推按
        LaunchImproveFromUrlAdapter adapter = new LaunchImproveFromUrlAdapter(getSupportFragmentManager(),urlImages);

        vp_launch.setAdapter(adapter);


    }


}