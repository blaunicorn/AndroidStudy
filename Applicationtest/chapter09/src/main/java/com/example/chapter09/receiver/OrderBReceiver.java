package com.example.chapter09.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OrderBReceiver extends BroadcastReceiver {

    public  static  final  String ORDER_ACTION = "com.example.chapter09.order";
    public static final  String TAG = "wcy";
    @Override
    public void onReceive(Context context, Intent intent) {
        // 一旦接收到标准广播，马上触发接收器的onReceive方法
        if (intent !=null && intent.getAction().equals(ORDER_ACTION)) {
            Log.d(TAG, "B onReceive: 收到一个有序广播");
            abortBroadcast(); // 有序广播可以中断， 此时后面的接收器就接收不到该广播
        }
    }
}
