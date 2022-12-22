package com.example.chapter02;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import com.example.chapter02.adapter.ImagePagerAdapter;

import java.util.ArrayList;

public class PageTabActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager vp_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_tab);
        
        // 初始化菜单栏
        initPagerStrip();
        // 初始化翻页视图
        initViewPager();
    }

    private void initViewPager() {
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
        // 设置默认选择第三个
        vp_content.setCurrentItem(3);
    }

    private void initPagerStrip() {
        PagerTabStrip pts_tab = findViewById(R.id.pts_tab);
        pts_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        pts_tab.setTextColor(Color.BLACK);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toast.makeText(this,"滑动到了第"+position+"页",LENGTH_SHORT).show();

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}