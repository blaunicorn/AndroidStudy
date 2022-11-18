package com.example.chapter06;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter06.utils.Utils;

import java.io.File;
import java.util.List;

public class ImageWriteActivity extends AppCompatActivity {

    private ImageView iv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_write);

        iv_content = findViewById(R.id.iv_content);

        findViewById(R.id.btn_save).setOnClickListener(this::clickFn);
        findViewById(R.id.btn_read).setOnClickListener(this::clickFn);
    }

    private void clickFn(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
               //从指定的资源文件中获取位图对象
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pexels_photo);
                // 获取当前app的私有下载目录
                String fileName = System.currentTimeMillis() + ".png";
                String path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separatorChar + fileName;
                Log.d("wcy", path);
//                把位图文件保存成文件
                Utils.saveImage(path, bitmap);
                Utils.show(this, "保存成功");
                break;
            case R.id.btn_read:
//
//                方式一 : 从指定文件读取图片文件
                    String path1 = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separatorChar + "1668242681781.png";
                    Log.d("wcy", "clickFn:读取 "+path1);
                    Bitmap bitmap1 = Utils.openImage(path1);
                    iv_content.setImageBitmap(bitmap1);

//                    方式二，直接调用decodeFile方法
                Bitmap bitmap2 = BitmapFactory.decodeFile(path1);
                iv_content.setImageBitmap(bitmap2);

//                方式三 ： 直接调用 setImageURI方法，设置图像视图的路径对象；
                iv_content.setImageURI(Uri.parse(path1));
                break;

            // 从外部文件中获取图片资源对象路径
        }
    }
}