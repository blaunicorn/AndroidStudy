package com.example.chapter08;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter08.utils.Utils;

import java.util.Arrays;

public class SpinnerDropdownActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // 定义下拉列表需要显示的文本数组
    private String[] starArray = new String[]{};
    private Spinner sp_dropdown;
    private Spinner sp_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_dropdown);
        //  程序目的，学习下拉列表的使用

        sp_dropdown = findViewById(R.id.sp_dropdown);
        sp_dialog = findViewById(R.id.sp_dialog);

        // 构造模拟数据
        for (int i = 0; i < 20; i++) {
            String title = "星星" + String.valueOf(i);
            starArray = Arrays.copyOf(starArray, starArray.length + 1);
            starArray[starArray.length - 1] = title;
        }
        Log.d("wcy", "onCreate: " + Arrays.toString(starArray));

        // 构建数组适配器，包含 条目布局和 数据数组
        // android.R.layout.simple_list_item_1 为系统默认布局
//        ArrayAdapter<String> startAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,starArray);
        // 为自定义布局, 布局内 需要只保留TextView元素包裹
        ArrayAdapter<String> startAdapter = new ArrayAdapter<>(this, R.layout.item_select, starArray);
        // 把构建成的适配器 应用到节点上
        sp_dropdown.setAdapter(startAdapter);
        // 设置下拉列表默认显示第一项
        sp_dropdown.setSelection(0);
        // 获取用户的选项，并设置监听器
        sp_dropdown.setOnItemSelectedListener(this);

        // dialog下可以设置标题
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(this, R.layout.item_select, starArray);
        //  dialog模式，可以设置标题
        sp_dialog.setPrompt("请选择：");
        // 可以应用反射设置 弹框的高度

        // 把构建成的适配器 应用到节点上
        sp_dialog.setAdapter(dialogAdapter);
        // 设置下拉列表默认显示第一项
        sp_dialog.setSelection(0);
        // 获取用户的选项，并设置监听器
        sp_dialog.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Utils.show(this, "你选择的是：" + starArray[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}