package com.example.myapplication_test;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DrawableShapeActivity extends AppCompatActivity {
    private View v_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_shape);
        v_content = findViewById(R.id.v_content);
        findViewById(R.id.btn_rect).setOnClickListener((View.OnClickListener) clickFn);
        findViewById(R.id.btn_oval).setOnClickListener((View.OnClickListener) clickFn);
        v_content.setBackgroundResource(R.drawable.shape_rect_gold);
    }

    private View.OnClickListener clickFn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
switch (v.getId()) {
    case R.id.btn_rect:
        v_content.setBackgroundResource(R.drawable.shape_rect_gold);
    break;
    case R.id.btn_oval:
        v_content.setBackgroundResource(R.drawable.shape_oval_rose);
        break;
    default:
        break;
}
        }
    };

}