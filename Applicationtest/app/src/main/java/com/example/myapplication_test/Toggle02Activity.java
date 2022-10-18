package com.example.myapplication_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class Toggle02Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle02);
        findViewById(R.id.button01).setOnClickListener(view -> {
//            finish();
            startActivity(new Intent(this,Toggle01Activity.class));
        });
        findViewById(R.id.iv_back).setOnClickListener(jumpBack);

        System.out.println("Toggle02Activity is Create");
//        拨号
//        String phoneNo = "12345";
//        Intent intent = new Intent(); // 创建一个新的意图
//        intent.setAction(Intent.ACTION_DIAL); // 设置意图动作为准备拨号
//        Uri uri = Uri.parse("tel:"+phoneNo); // 声明一个拨号的Uri
//        intent.setData(uri); // 设置意图前往的路径
//        startActivity(intent); // 启动意图活动页面
        //        发短信
        String phoneNo = "1234567890";
        Intent intent = new Intent(); // 创建一个新的意图
        intent.setAction(Intent.ACTION_SENDTO); // 设置意图动作为准备发短信
        Uri uri = Uri.parse("smsto:"+phoneNo); // 声明一个发短信的Uri
        intent.setData(uri); // 设置意图前往的路径
        startActivity(intent); // 启动意图活动页面
    }
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Toggle02Activity is start");
    }
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Toggle02Activity is Resume");

    }
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Toggle02Activity is Pause");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("Toggle02Activity is restart");
    }
    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("Toggle02Activity is Stop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Toggle02Activity is Destroy");
    }

    private View.OnClickListener jumpBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            finish();
        }
    };
}