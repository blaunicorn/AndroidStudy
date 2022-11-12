package com.example.chapter06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SqliteDatabaseActivity extends AppCompatActivity {

    private String mDatabasePath;
    private TextView tv_database;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_database);
        tv_database = findViewById(R.id.tv_database);
        findViewById(R.id.btn_database_create).setOnClickListener(this::onClickFn);
        findViewById(R.id.btn_database_delete).setOnClickListener(this::onClickFn);

        // 声明数据库路径
        mDatabasePath = getFilesDir() + "/test.db";
    }

    private void onClickFn(View view) {
        switch (view.getId()) {
            case R.id.btn_database_create:
//                创建或打开数据库，如果不存在则创建它,三个参数：数据库路径、模式、游标工厂
                db = openOrCreateDatabase(mDatabasePath, Context.MODE_PRIVATE, null);
                String desc = String.format("数据库%s创建%s", db.getPath(),(db !=null)?"成功":"失败");
                tv_database.setText(desc);
                break;
            case R.id.btn_database_delete:
                // 删除数据库
                boolean result = deleteDatabase(mDatabasePath);

                    String desc1 = String.format("数据库%s删除%s", mDatabasePath,result?"成功":"失败");
                    tv_database.setText(desc1);

                break;
        }
    }
}