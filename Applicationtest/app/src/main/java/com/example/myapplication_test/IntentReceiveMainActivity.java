package com.example.myapplication_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication_test.utils.Utils;

public class IntentReceiveMainActivity extends AppCompatActivity {

    private TextView tv_receive;
    private TextView tv_receive_to_send;
    private View.OnClickListener clickFn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_from_receive_to_send:
                    //do something
                    Intent intent = new Intent();
                    intent.setClass(IntentReceiveMainActivity.this, IntentReceiveMainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("response_time", Utils.getNowTime());
                    bundle.putString("response_content", tv_receive_to_send.getText().toString());
                    intent.putExtras(bundle);
                    setResult(AppCompatActivity.RESULT_OK,intent);
                    finish();
                    break;

            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_receive_main);
        tv_receive = findViewById(R.id.tv_receive);
        tv_receive_to_send = findViewById(R.id.tv_receive_to_send);
    // 获取上一个页面传过来的意图中的数据
        Intent intent = new Intent();
        Bundle bundle = getIntent().getExtras();
        String request_time = bundle.getString("request_time");
        String request_content = bundle.getString("request_content");
        String desc = String.format(" 收到请求消息：\n请求事件为%s\n请求内容为%s", request_time, request_content);
        tv_receive.setText(desc);

        Button btn_from_receive_to_send = findViewById(R.id.btn_from_receive_to_send);
        btn_from_receive_to_send.setOnClickListener(clickFn);


    }
}