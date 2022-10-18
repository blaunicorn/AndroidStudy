package com.example.myapplication_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Toggle01Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle01);
        findViewById(R.id.button1).setOnClickListener(this::jumpFn);
    }

    public void jumpFn(View v) {
        startActivity(new Intent(Toggle01Activity.this, Toggle02Activity.class));

    }

    ;
};
