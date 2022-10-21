package com.example.myapplication_test;

import static com.example.myapplication_test.R.raw.test;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication_test.utils.HttpServer;
import com.example.myapplication_test.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ning", "onCreate.");
        TextView tv = findViewById(R.id.tv);
//        方式一
        tv.setText("你好，世界！");
//        方式二
        tv.setText(R.string.second);
//        设置文本大小
        tv.setTextSize(30);
        //        设置文本颜色
//        tv.setTextColor(Color.GREEN);
        tv.setTextColor(0xff0000ff);

        //        延時操作
        new Handler().postDelayed(() -> {
            tv.setText("你好，新的世界！");
            // TODO
        }, 8000);

//        转跳
        Button button = findViewById(R.id.button_01);
        button.setOnClickListener(view -> {
            Intent intent = new Intent();
//            intent.setClass(MainActivity.this, MainActivity2.class);
            intent.setClass(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

//         子线程获取网络数据
        Button button_02 = findViewById(R.id.button_02);
        TextView tv_json = findViewById(R.id.tv_json);
//        //可滑动，会与xml的设置想冲突
//        tv_json.setMovementMethod(ScrollingMovementMethod.getInstance());
        button_02.setOnClickListener(view -> {
            Thread thread=new Thread(new Runnable() {
                String html = null;
                @Override
                public void run() {

                    try {
                        html = HttpServer.getHtml("http://www.weather.com.cn/data/cityinfo/101190408.html");
                        html = HttpServer.getHtml("https://movement.gzstv.com/news/detail/142186/");
                        Log.i("jsonData",html);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tv_json.post(() -> tv_json.setText(html));
                }

            });
            thread.start();
        });

//获取tv_code的布局参数（含宽度和高度),并设置,需要注意默认的px单位，需要把dp数值转为px数值
        TextView tv_code = findViewById(R.id.tv_code);
        ViewGroup.LayoutParams params = tv_code.getLayoutParams();
        params.width = Utils.dip2px(this,400);
        tv_code.setLayoutParams(params);

        Button button_04 = findViewById(R.id.button_04);
        button_04.setOnClickListener(getTxtFn);



        Button button_05 = findViewById(R.id.button_05);
        button_05.setOnClickListener(pastFn);

        // 启动复制
        copyClipboard();

        // 正则获取文本
        Button button_06 = findViewById(R.id.button_06);
        button_06.setOnClickListener(getRegexTxtFn);

    }

    // 获取文本
    private View.OnClickListener getTxtFn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            InputStream inputStream = getResources().openRawResource(test);
            String str = getString(inputStream);
             TextView tv_json = findViewById(R.id.tv_json);
            tv_json.setText(str);
        }
    };

    // 获取正则文本
    private View.OnClickListener getRegexTxtFn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            InputStream inputStream = getResources().openRawResource(test);
            String str = getString(inputStream);
            str = String.valueOf(regexString(str));
            TextView tv_json = findViewById(R.id.tv_json);
            tv_json.setText(str);
        }
    };

    // 查看剪贴板数据
    private View.OnClickListener pastFn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView tv_code = findViewById(R.id.tv_code);
//        从剪贴板获取数据
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            tv_code.setText(past());
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                startService(new Intent(MainActivity.this, FloatingButtonService.class));
            }
        }
    }
    public void startFloatingButtonService(View view) {
        Toast.makeText(this,Boolean.toString(FloatingButtonService.isStarted) , Toast.LENGTH_SHORT);
        if (FloatingButtonService.isStarted) {

            return;
        }
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        } else {
            startService(new Intent(MainActivity.this, FloatingButtonService.class));
        }
    }


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
    public static List<String> regexString(String text) {
        // 匹配字符串中@***
//        String REGEX = "(0-9){1,2}[.]";
        String test = "党的十九大";
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

    public void copyClipboard() {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", "这里是要复制的文字");
// 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }




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
};


