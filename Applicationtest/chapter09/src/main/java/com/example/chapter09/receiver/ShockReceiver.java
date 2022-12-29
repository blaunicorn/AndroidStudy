package com.example.chapter09.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

public class ShockReceiver extends BroadcastReceiver {
public static  final  String SHOCK_ACTION = "com.example.chapter09.shock";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent !=null && intent.getAction().equals(SHOCK_ACTION)) {
            Log.d("wcy", "onReceive: 震动");
            //  如果是真机，要从系统服务中获取震动管理器
            Vibrator vb = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vb.vibrate(500); // 震动500ms（需要设置系统权限)<uses-permission android:name="android.permission.VIBRATE" />

        }

//        throw new UnsupportedOperationException("Not yet implemented");
    }
}