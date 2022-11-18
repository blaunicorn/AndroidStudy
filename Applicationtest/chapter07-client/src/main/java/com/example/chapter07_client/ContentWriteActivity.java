package com.example.chapter07_client;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chapter07_client.entity.User;
import com.example.chapter07_client.utils.Utils;

import java.io.File;

public class ContentWriteActivity extends AppCompatActivity {
    private CheckBox ck_married;
    private EditText et_weight;
    private EditText et_height;
    private EditText et_age;
    private EditText et_name;
    private TextView tv_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_write);

        // 把内容保存到文本文件里

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);
        tv_txt = findViewById(R.id.tv_txt);

        findViewById(R.id.btn_save).setOnClickListener(this::onClickFn);
        findViewById(R.id.btn_read).setOnClickListener(this::onClickFn);
        findViewById(R.id.btn_delete).setOnClickListener(this::onClickFn);
    }
    @SuppressLint("Range")
    private void onClickFn(View view) {
        String name = et_name.getText().toString();
        String age = et_age.getText().toString();
        String height = et_height.getText().toString();
        String weight = et_weight.getText().toString();
        switch (view.getId()) {
            case R.id.btn_save:
                // 构建文本
                ContentValues values = new ContentValues();
                values.put(UserInfoContent.USER_NAME,name);
                values.put(UserInfoContent.USER_AGE,Integer.parseInt(age));
                values.put(UserInfoContent.USER_HEIGHT,Integer.parseInt(height));
                values.put(UserInfoContent.USER_WEIGHT,Float.parseFloat(weight));
                values.put(UserInfoContent.USER_MARRIED,ck_married.isChecked());
                getContentResolver().insert(UserInfoContent.CONTENT_URI,values);

                Utils.show(this, "保存成功");
                break;
            case R.id.btn_read:

                Cursor cursor = getContentResolver().query(UserInfoContent.CONTENT_URI, null, null, null);
               if (cursor!=null) {
                  while (cursor.moveToNext()) {
//                      构建user对象，并打印
                      User user = new User();
//                      user.id = cursor.getInt(cursor.getColumnIndex("_id"));
                      // 直接使用继承的属性，代替列名和列号
                      user.id = cursor.getInt(cursor.getColumnIndex(UserInfoContent._ID));
                      user.name = cursor.getString(cursor.getColumnIndex(UserInfoContent.USER_NAME));
                      user.age = cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_AGE));
                      user.height = cursor.getLong(cursor.getColumnIndex(UserInfoContent.USER_HEIGHT));
                      user.married = cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_MARRIED))==1?true:false;
                      Log.d("wcy", "onClickFn: "+ user.toString());
                  }
                  cursor.close();
               }
                break;
            case R.id.btn_delete:
                break;
        }
    }
}