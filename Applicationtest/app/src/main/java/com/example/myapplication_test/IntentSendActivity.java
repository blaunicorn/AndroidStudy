package com.example.myapplication_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication_test.utils.Utils;

public class IntentSendActivity extends AppCompatActivity {

    private TextView tv_send;
    private TextView tv_receive;
    private ActivityResultLauncher<Intent> register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_send);
        tv_send = findViewById(R.id.tv_send);
        tv_send.setText("这是发送给其他活动页的数据");
        Button btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(handler);

        tv_receive = findViewById(R.id.tv_receive);
        Button btn_send_and_receive = findViewById(R.id.btn_send_and_receive);
        btn_send_and_receive.setOnClickListener(returnHandler);


        //            startActivityForResult(intent,6666); // 已要废弃了
        // 注册下一页面返回时使用的回调方法
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
// 取出回调返回的数据
                if (result!=null) {
                    Intent intent =   result.getData();
                    if (intent!=null && result.getResultCode()== Activity.RESULT_OK) {
                        Bundle bundle = intent.getExtras();
                        String response_time= bundle.getString("response_time");
                        String response_content = bundle.getString("response_content");
                        String desc = String.format(" 收到响应消息：\n响应事件为%s\n响应内容为%s", response_time, response_content);
                        tv_receive.setText(desc);
                    }
                }
            }
        });


    }



//    上一页面传送给下一页面数据
    private View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_send:
                    //do something
                    Intent intent = new Intent();
                    intent.setClass(IntentSendActivity.this, IntentReceiveMainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("request_time", Utils.getNowTime());
                    bundle.putString("request_content",tv_send.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;

            }
        }

    };

    // 发送带返回函数的数据
    private View.OnClickListener returnHandler = new View.OnClickListener() {



        @Override
        public void onClick(View view) {
            Intent intent = new Intent(IntentSendActivity.this,IntentReceiveMainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("request_time",Utils.getNowTime());
            bundle.putString("request_content",tv_send.getText().toString());
            intent.putExtras(bundle);
//            把返回的消息放在视图上
            register.launch(intent);
        }
    };
}