package com.example.chapter02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.chapter02.adapter.LaunchSimpleAdapter;

public class LaunchSimpleActivity extends AppCompatActivity {

    // 声明引导页面的图片数组
    private int[] launchImageArray = {R.drawable.c03,R.drawable.c04,R.drawable.c05};
    private ViewPager vp_launch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_simple);

        vp_launch = findViewById(R.id.vp_launch);

//        构建适配器
        LaunchSimpleAdapter adapter = new LaunchSimpleAdapter(this,launchImageArray);

        vp_launch.setAdapter(adapter);
        Log.d("wcy", "onCreate: ");
    }
}