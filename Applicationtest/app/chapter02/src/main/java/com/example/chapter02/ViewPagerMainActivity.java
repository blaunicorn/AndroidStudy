package com.example.chapter02;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.chapter02.adapter.ImagePagerAdapter;

import java.util.ArrayList;

public class ViewPagerMainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager vp_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_main);
//        目的： 学习轮播图
        vp_content = findViewById(R.id.vp_content);
        // 通过适配器设置内容

        // 创建图片集合
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            arrayList.add("c0"+(i+1));
        }
        Log.d("wcy", "onCreate: "+ arrayList);
        ImagePagerAdapter adapter = new ImagePagerAdapter(this,arrayList);
        vp_content.setAdapter(adapter);
        vp_content.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//    在翻页过程中触发，第一个参数表示当前页面的序号；第二个参数表示页面偏移的百分比0-1；第三个参数表示页面的偏移距离
    }

    @Override
    public void onPageSelected(int position) {
        // 给页面视图添加 一个 变更的监听
//        翻页结束后触发，参数表示当前滑动到了哪一个页面
//        Toast.makeText(this,"滑动到了第"+position+"页",LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//    翻页状态改变时触发。state取值为 0 静止 1正在滑动 2滑动完毕
    }
}