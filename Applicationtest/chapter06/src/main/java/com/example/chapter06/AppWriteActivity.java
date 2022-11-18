package com.example.chapter06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AppWriteActivity extends AppCompatActivity {
    private CheckBox ck_married;
    private EditText et_weight;
    private EditText et_height;
    private EditText et_age;
    private EditText et_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_write);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);

        findViewById(R.id.btn_save).setOnClickListener(this::onClickFn);
        
//        启动app时读取
        reload();
    }

    private void reload() {
        MyApplication app =  MyApplication.getInstance();
        String name =  app.infoMap.get("name");
        if (name==null) {
            return;
        }
        String age =  app.infoMap.get("age");
        String height= app.infoMap.get("height");
        String weight =app.infoMap.get("weight");
        String married =app.infoMap.get("married");
        et_name.setText(name);
        et_age.setText(age);
        et_height.setText(height);
        et_weight.setText(weight);
        ck_married.setChecked(married.equals("是")?true:false);
    }

    private void onClickFn(View view) {
        String name = et_name.getText().toString();
        String age = et_age.getText().toString();
        String height = et_height.getText().toString();
        String weight = et_weight.getText().toString();

        if (name == null || age == null || height == null || weight == null) {
            Toast.makeText(this, "您的信息填写不全", Toast.LENGTH_SHORT).show();
            return;
        }
        MyApplication app =  MyApplication.getInstance();
        app.infoMap.put("name",name);
        app.infoMap.put("age",age);
        app.infoMap.put("height",height);
        app.infoMap.put("weight",weight);
        app.infoMap.put("married",ck_married.isChecked()?"是":"否");
    }
}