package com.example.myapplication_test;


import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication_test.utils.Utils;

public class ButtonActivity extends AppCompatActivity {

    private TextView tv_result;
    private Button btn02;
    private Button btn_06;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        tv_result = findViewById(R.id.tv);
        //方式一、监听方式
        btn02 = findViewById(R.id.btn02);
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String desc = String.format("%s 点击了按钮：%s", Utils.getNowTime(),((Button)view).getText());
                tv_result.setText(desc);
                Toast.makeText(ButtonActivity.this, "Clicked点击了btn02", Toast.LENGTH_SHORT).show();
            }
        });

//        复用的话，可以用新类
//        btn02.setOnClickListener(new MyOnClickListener());

        Button btn03 = findViewById(R.id.btn03);
        btn03.setOnLongClickListener(view -> {
            String desc = String.format("%s 点击了按钮：%s", Utils.getNowTime(),((Button)view).getText());
            tv_result.setText(desc);
            Toast.makeText(ButtonActivity.this, "Clicked点击了btn03", Toast.LENGTH_SHORT).show();
            return false;
        });

        //    使测试按钮可用或不可用
        Button btn_04 =  findViewById(R.id.btn04);
        Button btn_05 =  findViewById(R.id.btn05);
        btn_06 = findViewById(R.id.btn06);
        btn_05.setOnClickListener(this::caseClick);
        btn_04.setOnClickListener(this::caseClick);

        copyClipboard();
    }

        public void caseClick(View v) {
           switch(v.getId()) {
               case R.id.btn04:
//                   启动当前控件
                   btn_06.setText("测试按钮(可用)");
                   btn_06.setEnabled(true);
                   break;
               case R.id.btn05:
//                   禁用当前按钮
                   btn_06.setText("测试按钮(不可用)");
                   btn_06.setEnabled(false);
                   break;
           }
        }

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

    // 方式二、在xml中设置 android:onClick="doClick"
    public void doClick(View view) {
//        view类型转换成button类型，并获取文本
        String desc = String.format("%s 点击了按钮：%s", Utils.getNowTime(),((Button)view).getText());
        tv_result.setText(desc);
//        从剪贴板获取数据
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        tv_result.setText(past());
        Toast.makeText(ButtonActivity.this, "Clicked点击了", Toast.LENGTH_SHORT).show();
    }

     // 方式三 定义类
//    class MyOnClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            String desc = String.format("%s 在回调中点击了按钮：%s", Utils.getNowTime(),((Button)view).getText());
//            tv_result.setText(desc);
//            Toast.makeText(ButtonActivity.this, "Clicked点击了btn02", Toast.LENGTH_SHORT).show();
//        }
//    }


}