package com.example.chapter08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.example.chapter08.utils.Utils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpinnerIconActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
 private Spinner sp_icon_dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_icon);

        //  程序目的，学习带图标的下拉列表的使用


        sp_icon_dialog = findViewById(R.id.sp_icon_dialog);

        // 构造模拟数据
        String[] starArray = new String[]{};
        for (int i = 0; i < 14; i++) {
            String title = "星星" + String.valueOf(i);
            starArray = Arrays.copyOf(starArray, starArray.length + 1);
            starArray[starArray.length - 1] = title;
        }
        int[] iconArray = new int[]{};
        for (int i = 0; i < 14; i++) {
            String title = "c_" + (i<10?"0"+i:i);
            iconArray = Arrays.copyOf(iconArray, iconArray.length + 1);
            // 动态引用R.drawable.XXX图片
            iconArray[iconArray.length - 1] =getResources().getIdentifier(title,"drawable",getPackageName());
        }
//        // 声明一个映射对象列表，用于保存图标和名称的配对信息
        List<Map<String,Object>> list = new ArrayList<>();
//        遍历放入 iconArray是图标数据，starArray是名称数组
        for (int i=0;i<iconArray.length;i++
             ) {
            Map<String,Object> item = new HashMap<>();
            item.put("icon",iconArray[i]);
            item.put("name",starArray[i]);
            list.add(item);
        }
        // 构建适配器
        SimpleAdapter startAdapter = new SimpleAdapter(this,list,R.layout.item_simple,
                new String[]{"icon","name"},new int[]{R.id.iv_icon,R.id.tv_name});
        sp_icon_dialog.setAdapter(startAdapter);
        sp_icon_dialog.setSelection(0);
        sp_icon_dialog.setOnItemSelectedListener(this);
        Log.d("wcy", "onCreate: " + Arrays.toString(starArray));
        Log.d("wcy", "onCreate: " + Arrays.toString(iconArray));
        Log.d("wcy", "onCreate: " + list.toString());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Utils.show(this,"选择的"+i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}