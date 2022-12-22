package com.example.chapter02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        先判断是否第一次启动app，如果是，则进入引导页(左右滑动切换查看，滑动到最后一页点击按钮进入首页)。
//        如果不是，则直接进入首页
        SharedPreferences sp=getSharedPreferences("name",MODE_PRIVATE);
        boolean is=sp.getBoolean("ok",true);
        //判断是否为第一次打开软件
        if (is){

            //跳转到引导页
            startActivity(new Intent(MainActivity.this,LaunchImproveActivity.class));
            finish();
        }else {
            //直接进入首页
//            startActivity(new Intent(this,MainActivity.class));
//            finish();
        }

    }
}