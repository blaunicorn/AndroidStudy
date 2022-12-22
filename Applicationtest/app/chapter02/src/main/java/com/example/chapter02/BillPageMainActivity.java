package com.example.chapter02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.chapter02.adapter.BillPagerAdpater;
import com.example.chapter02.utils.DateUtils;

import java.util.Calendar;

public class BillPageMainActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private TextView tv_month;
    private Calendar calendar;
    private ViewPager vp_bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_page_main);

        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_option = findViewById(R.id.tv_option);

        tv_title.setText("账单列表");
        tv_option.setText("增加账单");

        tv_month = findViewById(R.id.tv_month);
        // 显示当前月份
        calendar = Calendar.getInstance();
        tv_month.setText(DateUtils.getMonth(calendar));
        tv_month.setOnClickListener(this);

        // 初始化翻页视图
        initViewPager();
    }

    private void initViewPager() {
        // 从布局视图中获取翻页标签栏
        PagerTabStrip pts_bill = findViewById(R.id.pts_bill);
        pts_bill.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
        vp_bill = findViewById(R.id.vp_bill);
        BillPagerAdpater adapter = new BillPagerAdpater(getSupportFragmentManager());
        vp_bill.setAdapter(adapter);
        vp_bill.setCurrentItem(calendar.get(Calendar.MONTH));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_month:
                // 弹出日期月份对话框
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        (Context) this,
                        this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
                break;
        }
    }

    // 选择时间后的回调函数，
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        //设置给文本显示
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tv_month.setText(DateUtils.getMonth(calendar));
        // 设置翻页视图显示第几页
        vp_bill.setCurrentItem(month);
    }
}