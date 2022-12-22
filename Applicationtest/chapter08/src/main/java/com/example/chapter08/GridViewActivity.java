package com.example.chapter08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.chapter08.adapter.PlanetBaseAdapter;
import com.example.chapter08.adapter.PlanetGridAdapter;
import com.example.chapter08.entity.Planet;
import com.example.chapter08.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class GridViewActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private GridView gv_planet;
    private List<Planet> planetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        gv_planet = findViewById(R.id.gv_planet);

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
//        定义适配器
        PlanetGridAdapter adapter = new PlanetGridAdapter(this, planetList);
//        设置适配器
        gv_planet.setAdapter(adapter);
        // 设置子组件点击事件
        gv_planet.setOnItemClickListener(this);

        // 设置条目宽度
        gv_planet.setColumnWidth(100);
        // 设置拓展模式（四种模式）
//        gv_planet.setStretchMode(GridView.STRETCH_SPACING);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Utils.show(this,"您选择了："+ planetList.get(i).name);
    }
}