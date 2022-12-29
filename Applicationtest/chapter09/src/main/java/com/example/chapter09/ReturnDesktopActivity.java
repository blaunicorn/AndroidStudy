package com.example.chapter09;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PictureInPictureParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Rational;

public class ReturnDesktopActivity extends AppCompatActivity {

    public String TAG = "wcy";
    private DesktopReceiver desktopReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_desktop);

        desktopReceiver = new DesktopReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(desktopReceiver,filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(desktopReceiver);
    }

    // 进入画中画模式或退出华中黄模式的回调触发
    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
            Log.d(TAG, "onPictureInPictureModeChanged: 进入");
        } else  {
            Log.d(TAG, "onPictureInPictureModeChanged: 退出");
        }
    }

    @Override
    public boolean onPictureInPictureRequested() {
        return super.onPictureInPictureRequested();
    }

    // 定义一个返回到桌面或者切换到任务列表的广播接收器
    private  class DesktopReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent !=null && intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra("reason");
                if (!TextUtils.isEmpty(reason) && (reason.equals("homekey") || reason.equals("recentapps"))) {
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O && !isInPictureInPictureMode()) {
//                        Android >8.0 并且不是处于画中画模式
//                        进入画中画模式
                        PictureInPictureParams.Builder builder = new PictureInPictureParams.Builder();
//                       指定宽高比例值 10/5
                        Rational rational = new Rational(10,5);
                        builder.setAspectRatio(rational);
                        enterPictureInPictureMode(builder.build());
                    }
                }
            }
        }
    }
}