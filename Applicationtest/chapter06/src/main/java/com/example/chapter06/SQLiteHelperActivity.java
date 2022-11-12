package com.example.chapter06;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter06.database.UserDBHelper;
import com.example.chapter06.entity.User;
import com.example.chapter06.utils.Utils;

import java.util.List;

public class SQLiteHelperActivity extends AppCompatActivity {
    private CheckBox ck_married;
    private EditText et_weight;
    private EditText et_height;
    private EditText et_age;
    private EditText et_name;
    private UserDBHelper mHelper;
    private TextView tv_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_helper);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);
        tv_users = findViewById(R.id.tv_users);

        findViewById(R.id.btn_save).setOnClickListener(this::onClickFn);
        findViewById(R.id.btn_delete).setOnClickListener(this::onClickFn);
        findViewById(R.id.btn_update).setOnClickListener(this::onClickFn);
        findViewById(R.id.btn_query_all).setOnClickListener(this::onClickFn);
        findViewById(R.id.btn_query).setOnClickListener(this::onClickFn);
    }

    //  在页面生成后，构建数据库实例
    @Override
    protected void onStart() {
        super.onStart();
        // 获得数据库帮助器的实例
        mHelper = UserDBHelper.getInstance(this);
//        打开读写链接
        mHelper.openReadLink();
        mHelper.openWriteLink();
    }

    // 在资源回收之前，销毁数据库实例
    @Override
    protected void onStop() {
        super.onStop();
        mHelper.closeLink();
    }

    private void onClickFn(View view) {
        String name = et_name.getText().toString();
        String age = et_age.getText().toString();
        String height = et_height.getText().toString();
        String weight = et_weight.getText().toString();

        User user = null;
        switch (view.getId()) {
            case R.id.btn_save:
                user = new User(name, Integer.parseInt(age), Long.parseLong(height), Float.parseFloat(weight), ck_married.isChecked());
                if (mHelper.insert(user) > 0) {
                    Utils.show(this, "添加成功");
                }
                ;
                break;
            case R.id.btn_delete:
                //  根据名字删除
                if (mHelper.deleteByName(name) > 0) {
                    Utils.show(this, "删除成功！");
                } else {
                    Utils.show(this, "未查到该姓名");
                }
                break;
            case R.id.btn_update:
                //  根据名字查询并修改
                user = new User(name, Integer.parseInt(age), Long.parseLong(height), Float.parseFloat(weight), ck_married.isChecked());
                if (mHelper.update(user) > 0) {
                    Utils.show(this, "更新成功！");
                } else {
                    Utils.show(this, "未查到该姓名");
                }
                break;
            case R.id.btn_query_all:
//                查询所有
                Log.d("wcy", "查询所有");
                List<User> list = mHelper.queryAll();
                String desc = list.toString();
                for (User u : list) {
                    Log.d("wcy", u.toString());
//                    desc = desc + u.toString();
                }
                tv_users.setText(desc);
                break;
            case R.id.btn_query:
                Log.d("wcy","查询姓名");
                if (name.length()<=0) {
                    Utils.show(this, "名字不能为空！");
                    break;
                }
                List<User> list1 = mHelper.queryByName(name);
                tv_users.setText(list1.toString());
                Utils.show(this, "查询完成！");
                break;

        }
    }
}