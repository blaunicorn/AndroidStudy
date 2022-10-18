package com.example.myapplication_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //        转跳
        Button button = findViewById(R.id.button_02);
        button.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(SecondActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}