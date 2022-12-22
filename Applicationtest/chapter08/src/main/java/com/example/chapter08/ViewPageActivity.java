package com.example.chapter08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

public class ViewPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);
        // 程序目的：学习ViewPager做轮播图

        ViewPager vp_content = findViewById(R.id.vp_content);

        PagerAdapter adapter = null;
        vp_content.setAdapter(adapter);

    }
}