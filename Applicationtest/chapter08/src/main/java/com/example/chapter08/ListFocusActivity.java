package com.example.chapter08;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter08.adapter.PlanetListWithButtonBaseAdapter;
import com.example.chapter08.entity.Planet;
import com.example.chapter08.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class ListFocusActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv_planet;
    private List<Planet> planetList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_focus);

        // 程序目的：实现带按钮的条目列表，并且条目也可以点击
        lv_planet = findViewById(R.id.lv_planet);

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
//        创建构造器
        PlanetListWithButtonBaseAdapter adapter = new PlanetListWithButtonBaseAdapter(this, planetList);
//        添加构造器
        lv_planet.setAdapter(adapter);

        lv_planet.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Utils.show(this,"条目被点击了。"+ planetList.get(position).name);
    }
}