package com.example.chapter09.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    private final Context mContent;
    public  static  final  String ALARM_ACTION = "com.example.chapter09.alarm";
    // 扩展构造方法，获取上下文
    public  AlarmReceiver(Context context) {
        super();
        this.mContent = context;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        
        if (intent!=null && intent.getAction().equals(ALARM_ACTION)) {
            Log.d("wcy", "onReceive: 收到闹钟广播");
        }
        
    }

    //  设置要给发送闹钟广播的方法
    public  void  sendAlarm() {
        Intent intent = new Intent(ALARM_ACTION);

//        创建一个用于广播的延迟意图，延迟意图的构建需要上下文和意图
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContent,0,intent,PendingIntent.FLAG_IMMUTABLE);
        // 从系统服务中获取闹钟管理器
        AlarmManager  alarmManager = (AlarmManager) mContent.getSystemService(Context.ALARM_SERVICE);
//       设置一次性闹钟，延迟若干秒收，携带延迟意图发送闹钟广播，但在android6.0后，set方法在暗屏时不保证发送广播，
//        必须调用setAndAllowWhileIdle方法
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,1000,pendingIntent);
        } else  {
            alarmManager.set(AlarmManager.RTC_WAKEUP,1000,pendingIntent);
        }

        // set 设置 setAndAllowWhileIdle设置一次性定时器 setRepeating 设置重复定时器 cancel取消指定延迟意图的定时器
    }
}