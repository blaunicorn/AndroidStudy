package com.example.chapter09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.example.chapter09.receiver.OrderAReceiver;
import com.example.chapter09.receiver.OrderBReceiver;

public class BroadOrderActivity extends AppCompatActivity implements View.OnClickListener {

    public  static  final  String ORDER_ACTION = "com.example.chapter09.order";
    private OrderBReceiver orderBReceiver;
    private OrderAReceiver orderAReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_order);

        // 了解有序广播，模拟创建两个接收者OrderAReceiver 和 OrderBReceiver
        findViewById(R.id.btn_send_order).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
// 创建一个指定的动作意图
        Intent intent = new Intent(ORDER_ACTION);
        // 第二个参数为需要的权限
        sendOrderedBroadcast(intent,null);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        多个接收器处理有序广播的顺序为：
//        优先级越大的接收器，越早收到有序广播
//        优先级相同的时候，早注册的早接收到有序广播
        orderAReceiver = new OrderAReceiver();
        IntentFilter filterA = new IntentFilter(ORDER_ACTION);
//        也可以自定义优先级,不设也可以
        filterA.setPriority(6);
        registerReceiver(orderAReceiver,filterA);

        orderBReceiver = new OrderBReceiver();
        IntentFilter filterB = new IntentFilter(ORDER_ACTION);
        filterB.setPriority(7);
        registerReceiver(orderBReceiver,filterB);
    }

//
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(orderAReceiver);
        unregisterReceiver(orderBReceiver);
    }
}