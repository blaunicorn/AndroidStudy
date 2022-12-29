package com.example.chapter09.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StandardReceiver extends BroadcastReceiver {

    public  static  final  String STANDARD_ACTION = "com.example.chapter09.standard";
    public static final  String TAG = "wcy";
    @Override
    public void onReceive(Context context, Intent intent) {
        // 一旦接收到标准广播，马上触发接收器的onReceive方法
        if (intent !=null && intent.getAction().equals(STANDARD_ACTION)) {
            Log.d(TAG, "onReceive: 收到一个标准广播");
        }
    }
}
