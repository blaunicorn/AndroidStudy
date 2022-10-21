package com.example.myapplication_test;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.example.myapplication_test.R.raw.test;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FloatingButtonService extends Service {
    public static boolean isStarted = false;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private Button button;
    private View floatingView;
    private Timer timer;

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
        timer.cancel();
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

            floatingView.findViewById(R.id.iv_search).setOnClickListener(getRegexTxtFn);
           timer = new Timer();
           TimerTask task = new TimerTask() {
               @Override
               public void run() {
                   getRegexTxtFn1();
               }
           };
            timer.schedule(task,1*1000,2*1000);

        }
    }

    private void getRegexTxtFn1() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (null == clipboardManager) { return;}
        InputStream inputStream = getResources().openRawResource(test);
        String str = getString(inputStream);
        str = String.valueOf(regexString(str,past()));
        TextView tv_content = floatingView.findViewById(R.id.tv_content);
        tv_content.setText(str);
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

    // 获取正则文本
    private View.OnClickListener getRegexTxtFn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            InputStream inputStream = getResources().openRawResource(test);
            String str = getString(inputStream);
            str = String.valueOf(regexString(str,past()));
            TextView tv_content = floatingView.findViewById(R.id.tv_content);
            tv_content.setText(str);
        }
    };
    // 读取txt文件
    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        long currLines = 0;
        try {
            while ((line = reader.readLine()) != null) {
                currLines++;
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //正则匹配文本
    @SuppressLint("ResourceAsColor")
    public static List<String> regexString(String text,String keyword) {
        // 匹配字符串中@***
//        String REGEX = "(0-9){1,2}[.]";
//        String test = "党的十九大";
        String test = keyword;
        // 包含回车等任意字符串
        String REGEX = "([0-9]{1,2}\\.)(.*[\\s\\S]{0,2}.*.*[\\s\\S]{0,2}.*)";
//        text = "1.什么；\n2.什么242342";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(text);
        // 若要改变颜色，则需用到SpannableString
        SpannableString sp = new SpannableString(text);
        String group = null;
        List<String> datelist = new ArrayList<String>();
//        System.out.println(text);
        while (matcher.find()) {

            group = matcher.group(1) + matcher.group(2);
            sp.setSpan(new ForegroundColorSpan(R.color.purple_700), matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            int result = group.indexOf(test);
            System.out.println("测试" + group);
            if (result!=-1) {
                System.out.println("字符串str中包含子串“ab”"+group);
                datelist.add(group);
            }

        }

        return  datelist;

    };
    // 查看剪贴板数据
    private View.OnClickListener pastFn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView tv_content = floatingView.findViewById(R.id.tv_content);
//        从剪贴板获取数据
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            tv_content.setText(past());

        }
    };
    //    粘贴（获取剪贴板内容）
    //获取系统剪贴板服务
    public String past() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (null != clipboardManager) {
            // 获取剪贴板的剪贴数据集
            ClipData clipData = clipboardManager.getPrimaryClip();
            if (null != clipData && clipData.getItemCount() > 0) {
                // 从数据集中获取（粘贴）第一条文本数据
                ClipData.Item item = clipData.getItemAt(0);
                if (null != item) {
                    String content = item.getText().toString();
                    return content;
                }
            }
        }
        return "";
    }
}
