package com.example.chapter06;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter06.utils.Utils;

import java.io.File;

public class FileWriteActivity extends AppCompatActivity {
    private CheckBox ck_married;
    private EditText et_weight;
    private EditText et_height;
    private EditText et_age;
    private EditText et_name;

    private String path;
    private TextView tv_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_write);

        // 把内容保存到文本文件里

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);
        tv_txt = findViewById(R.id.tv_txt);

        findViewById(R.id.btn_save).setOnClickListener(this::onClickFn);
        findViewById(R.id.btn_read).setOnClickListener(this::onClickFn);
    }

    private void onClickFn(View view) {
        String name = et_name.getText().toString();
        String age = et_age.getText().toString();
        String height = et_height.getText().toString();
        String weight = et_weight.getText().toString();
        switch (view.getId()) {
            case R.id.btn_save:
                // 构建文本
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("姓名：").append(name);
                stringBuilder.append("\n年龄：").append(age);
                stringBuilder.append("\n身高：").append(height);
                stringBuilder.append("\n体重：").append(weight);
                stringBuilder.append("\n婚否：").append(ck_married.isChecked() ? "是" : "否");

                // 文件名
                String fileName = System.currentTimeMillis() + ".txt";

                String directory = null;
                // 存入私有空间
                directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
                // 存入公共控件,存入公共路径 需要读写磁盘权限 WRITE_EXTERNAL_STORAGE
//                    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
                //   <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"></uses-permission>

                directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                path = directory + File.separatorChar + fileName;
//  内部存储私有空间
                directory = getFilesDir().toString();

                Utils.saveText(path, stringBuilder.toString());
                Utils.show(this, "保存成功");
                Log.d("wcy", path);
                break;
            case R.id.btn_read:
                tv_txt.setText(Utils.openTxt(path));
                break;
        }
    }
}