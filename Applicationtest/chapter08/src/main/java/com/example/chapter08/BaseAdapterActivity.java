package com.example.chapter08;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter08.adapter.PlanetBaseAdapter;
import com.example.chapter08.entity.Planet;
import com.example.chapter08.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class BaseAdapterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private List<Planet> planetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_adapter);
//        程序目的，学习baseAdapter的使用
//        baseAdapter是一个抽象类，不能直接使用，需要实体化，所以新建一个PlanetBaseAdapter实体类 和 初始化PlanetList数据

        Spinner sp_planet = findViewById(R.id.sp_icon_dialog);

//         创建PlanetList集合
        // 构造模拟数据
        String[] nameArray = new String[]{};
        int[] iconArray = new int[]{};
        String[] descArray = new String[]{};
        for (int i = 0; i < 14; i++) {
            String title = "星星" + String.valueOf(i);
            String desc = "简介简介简介" + String.valueOf(i);
            nameArray = Arrays.copyOf(nameArray, nameArray.length + 1);
            descArray = Arrays.copyOf(descArray, descArray.length + 1);
            nameArray[nameArray.length - 1] = title;
            descArray[descArray.length - 1] = desc;
            String icon = "c_" + (i < 10 ? "0" + i : i);
            iconArray = Arrays.copyOf(iconArray, iconArray.length + 1);
            // 动态引用R.drawable.XXX图片
            iconArray[iconArray.length - 1] = getResources().getIdentifier(icon, "drawable", getPackageName());
        }

        planetList = Planet.getDefaultList(nameArray, iconArray, descArray);
        PlanetBaseAdapter adapter = new PlanetBaseAdapter(this, planetList);
        sp_planet.setAdapter(adapter);
        sp_planet.setSelection(0);
        sp_planet.setOnItemSelectedListener(this);
        Log.d("wcy", "onCreate: " + planetList.toString());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Utils.show(this, "选择的" + planetList.get(i).name);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
