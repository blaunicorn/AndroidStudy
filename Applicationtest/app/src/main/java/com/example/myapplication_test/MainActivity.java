package com.example.myapplication_test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication_test.utils.HttpServer;
import com.example.myapplication_test.utils.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ning", "onCreate.");
        TextView tv = findViewById(R.id.tv);
//        方式一
        tv.setText("你好，世界！");
//        方式二
        tv.setText(R.string.second);
//        设置文本大小
        tv.setTextSize(30);
        //        设置文本颜色
//        tv.setTextColor(Color.GREEN);
        tv.setTextColor(0xff0000ff);

        //        延時操作
        new Handler().postDelayed(() -> {
            tv.setText("你好，新的世界！");
            // TODO
        }, 8000);

//        转跳
        Button button = findViewById(R.id.button_01);
        button.setOnClickListener(view -> {
            Intent intent = new Intent();
//            intent.setClass(MainActivity.this, MainActivity2.class);
            intent.setClass(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

//         子线程获取网络数据
        Button button_02 = findViewById(R.id.button_02);
        TextView tv_json = findViewById(R.id.tv_json);
        button_02.setOnClickListener(view -> {
            Thread thread=new Thread(new Runnable() {
                String html = null;
                @Override
                public void run() {

                    try {
                        html = HttpServer.getHtml("http://www.weather.com.cn/data/cityinfo/101190408.html");
                        Log.i("jsonData",html);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tv_json.post(() -> tv_json.setText(html));
                }

            });
            thread.start();
        });

//获取tv_code的布局参数（含宽度和高度),并设置,需要注意默认的px单位，需要把dp数值转为px数值
        TextView tv_code = findViewById(R.id.tv_code);
        ViewGroup.LayoutParams params = tv_code.getLayoutParams();
        params.width = Utils.dip2px(this,400);
        tv_code.setLayoutParams(params);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                startService(new Intent(MainActivity.this, FloatingButtonService.class));
            }
        }
    }
    public void startFloatingButtonService(View view) {
        Toast.makeText(this,Boolean.toString(FloatingButtonService.isStarted) , Toast.LENGTH_SHORT);
        if (FloatingButtonService.isStarted) {

            return;
        }
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        } else {
            startService(new Intent(MainActivity.this, FloatingButtonService.class));
        }
    }
}


