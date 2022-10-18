package com.example.myapplication_test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ImageView iv_scale = findViewById(R.id.image_01);
        //        延時操作
        new Handler().postDelayed(() -> {
            // TODO
            iv_scale.setImageResource(R.drawable.ic_launcher_foreground);
            iv_scale.setBackgroundColor(0xff0000ff);
            iv_scale.setScaleType(ImageView.ScaleType.CENTER);
        }, 3000);

        //控制button上图标大小
        Button button_01 = findViewById(R.id.button_01);
        Drawable drawable1 = getResources().getDrawable(R.drawable.xin_yingxiong);
        drawable1.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        button_01.setCompoundDrawables(drawable1, null, null, null);//只放左边


    }
}