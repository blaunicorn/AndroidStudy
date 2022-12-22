package com.example.chapter02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter02.database.BillDBHelper;
import com.example.chapter02.entity.BillInfo;
import com.example.chapter02.utils.DateUtils;

import java.util.Calendar;

public class BillAddActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private TextView tv_date;
    private Calendar calendar;
    private RadioGroup rg_type;
    private EditText et_remark;
    private EditText et_amount;
    private BillDBHelper billDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);

        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_option = findViewById(R.id.tv_option);

        tv_title.setText("请填写账单");
        tv_option.setText("账单列表");
        tv_option.setOnClickListener(this);

        tv_date = findViewById(R.id.tv_date);
        rg_type = findViewById(R.id.rg_type);
        et_remark = findViewById(R.id.et_remark);
        et_amount = findViewById(R.id.et_amount);
        Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);

        // 显示当前日期
        calendar = java.util.Calendar.getInstance();
        tv_date.setText(DateUtils.getDate(calendar));
        // 设置点击事件 弹出时间选择框
        tv_date.setOnClickListener(this);


        billDBHelper = BillDBHelper.getInstance(this);
        billDBHelper.openReadLink();
        billDBHelper.openWriteLink();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_date:
                // 弹出日期对话框
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        (Context) this,
                        this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
                break;
            case R.id.btn_save:
                // 保存订单信息
                Log.d("wcy", String.valueOf(rg_type.getCheckedRadioButtonId()));
                BillInfo bill = new BillInfo();
                bill.date = tv_date.getText().toString();
                bill.type = rg_type.getCheckedRadioButtonId() == R.id.rb_income ?
                        BillInfo.BILL_TYPE_INCOME : BillInfo.BILL_TYPE_COST;
                bill.remark = et_remark.getText().toString();
                bill.amount = Double.parseDouble(et_amount.getText().toString());

                if (billDBHelper.save(bill)>0) {
                    Toast.makeText(this,"添加账单成功",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_option:
                Intent intent = new Intent(this,BillPageMainActivity.class);
                startActivity(intent);
            default:
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
        tv_date.setText(DateUtils.getDate(calendar));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        billDBHelper.closeLink();
    }
}