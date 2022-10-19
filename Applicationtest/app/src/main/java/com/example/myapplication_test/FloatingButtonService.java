package com.example.myapplication_test;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class FloatingButtonService extends Service {
    public static boolean isStarted = false;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private Button button;
    private View floatingView;

    @Override
    public void onCreate() {
        super.onCreate();
        isStarted = true;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = 600;
        layoutParams.height = 600;
        layoutParams.x = 100;
        layoutParams.y = 100;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand , " + startId);
        if (floatingView == null) {
            Log.d(TAG, "onStartCommand: 创建悬浮窗");
            showFloatingWindow();
        }
        return super.onStartCommand(intent, flags, startId);

    }
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
    private void showFloatingWindow() {
        if (Settings.canDrawOverlays(this)) {
            Log.d(TAG, "showFloatingWindow: ");
//            button = new Button(getApplicationContext());
//            button.setText("Floating Window");
//            button.setBackgroundColor(Color.BLUE);
//            windowManager.addView(button, layoutParams);
            floatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_window, null);
            windowManager.addView(floatingView, layoutParams);
            floatingView.setOnTouchListener(new FloatingOnTouchListener());
                        // 点击浮窗的右上角关闭按钮可以关闭浮窗
            floatingView.findViewById(R.id.iv_close).setOnClickListener(view -> {

                Toast.makeText(FloatingButtonService.this, "Clicked点击了", Toast.LENGTH_SHORT).show();
                windowManager.removeView(floatingView);
                floatingView = null;
                isStarted = false;
            });
            WebView webView =  floatingView.findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("https://www.tisuba.com");
        }
    }

    // 实现浮窗的拖动功能, 通过改变layoutParams来实现
    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}
