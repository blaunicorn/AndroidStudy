package com.example.chapter08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.example.chapter08.adapter.PlanetBaseAdapter;
import com.example.chapter08.entity.Planet;
import com.example.chapter08.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {

    private List<Planet> planetList;
    private CheckBox ck_divider;
    private CheckBox ck_selector;
    private ListView lv_planet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // 程序目的： 学习ListView列表视图

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
        PlanetBaseAdapter adapter = new PlanetBaseAdapter(this, planetList);
        lv_planet.setAdapter(adapter);

        lv_planet.setOnItemClickListener(this);

//        设置checkbox
        ck_divider = findViewById(R.id.ck_divider);
        ck_selector = findViewById(R.id.ck_selector);
        ck_divider.setOnCheckedChangeListener(this);
        ck_selector.setOnCheckedChangeListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Utils.show(this,"选择的是："+planetList.get(i).name);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.ck_divider:
                // 显示分割线
                if (ck_divider.isChecked()) {
//                    从资源文件获取图形对象
                    lv_planet.setDivider(getResources().getDrawable(R.color.black,getTheme()));
                    lv_planet.setDividerHeight(Utils.dip2px(this,5));
                } else {
                    lv_planet.setDivider(null);
                    lv_planet.setDividerHeight(Utils.dip2px(this,2));
                }
                break;
            case R.id.ck_selector:
                if (ck_selector.isChecked()) {
//                    显示按压背景
                    lv_planet.setSelector(R.drawable.list_selector);
                } else {
                    lv_planet.setSelector(getResources().getDrawable(R.color.transparent,getTheme()));
                }
                break;
        }
    }
}