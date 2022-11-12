package com.example.myapplication_test;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.myapplication_test.utils.Utils;

import java.util.Calendar;

public class DrawableStateActivity extends AppCompatActivity {

    private CheckBox checkbox_custom;
    private TextView tv_result;
    private TextView tv_textView5;
    private EditText et_phone;
    private Button btn_login;
    private DatePicker dp_datePicker;
    private TextView tv_date;
    private TextView tv_date2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_state);
        CheckBox checkbox_system = findViewById(R.id.checkBox_system);
        checkbox_custom = findViewById(R.id.checkbox_custom);

//        checkbox_system.setOnCheckedChangeListener(this);
//        checkbox_custom.setOnCheckedChangeListener(this);

        SwitchCompat switch_status = findViewById(R.id.switch_status);
        tv_result = findViewById(R.id.tv_result);

        CheckBox ck_status = findViewById(R.id.ck_status);

        ck_status.setOnCheckedChangeListener(this::clickFn);

        // 多选框
        RadioGroup rb_gender = findViewById(R.id.rg_gender);
        rb_gender.setOnCheckedChangeListener(this::radioGroupFn);
        tv_textView5 = findViewById(R.id.textView5);

//        获取手机号长度和密码长度，并设置监听事件，判断长度是否符合要求
        et_phone = findViewById(R.id.editText5);
        EditText et_password = findViewById(R.id.editText2);
        et_password.setOnFocusChangeListener(this::onFocusFn);

        // 监听et_phone文本框文本位数，自动关闭软键盘
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
        et_password.addTextChangedListener(new HideTextWatcher(et_password, 6));

        // 弹出对话框
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this::onClickFn);

        // 给时间选择按钮注册监听事件
        findViewById(R.id.btn_ok).setOnClickListener(this::onClickTimeFn);
        // 获取时间选择器
        dp_datePicker = findViewById(R.id.datePicker);
        tv_date = findViewById(R.id.tv_date);
        tv_date2 = findViewById(R.id.tv_date2);
//        dp_datePicker.setOnDateChangedListener(this::dateFn);

        findViewById(R.id.btn_date).setOnClickListener(this::onClickTimeFn);

    }

    private void onDateSetFn(DatePicker datePicker, int year, int month, int dayOfMonth) {
//        int currentYear = dp_datePicker.getYear();
//        int month = dp_datePicker.getMonth() + 1;
//        int day = dp_datePicker.getDayOfMonth();
//        tv_date2.setText(String.format("setOnDateChangedListener监听的日期是%d年%d月%日", currentYear, month, day));
        String desc = String.format("您选择的日期是%d年%d月%d日", year, month+1, dayOfMonth);
        tv_date.setText(desc);
    }

    private void onClickTimeFn(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                String desc = String.format("您选择的日期是%d年%d月%d日", dp_datePicker.getYear(), dp_datePicker.getMonth() + 1, dp_datePicker.getDayOfMonth());
                tv_date.setText(desc);
                break;
            case R.id.btn_date:
                //  获取日历实例，包含当前的年月日
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog((Context) this, (DatePickerDialog.OnDateSetListener) this::onDateSetFn, year, month, dayOfMonth);

                // 显示日期对话框
                dialog.show();

                break;
        }
    }

    private void onClickFn(View view) {
        // 创建提醒对话框的构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("尊敬的用户");
        builder.setMessage("确定要卸载吗");

        // 设置按钮
        builder.setPositiveButton("卸载", (dialogInterface, i) -> {
            tv_textView5.setText("再见了");
            tv_textView5.setTextColor(android.graphics.Color.RED);
        });
        builder.setNegativeButton("再想想", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tv_textView5.setText("谢谢你的仁慈");
                tv_textView5.setTextColor(Color.GREEN);
            }
        });
        // 根据构造器创建对话框对象
        AlertDialog dialog = builder.create();
        // 显示对话框
        dialog.show();
    }


    private void onFocusFn(View view, boolean hasFocus) {
        if (hasFocus) {
            String phone = et_phone.getText().toString();
//           判读手机号是否为11位
            if (TextUtils.isEmpty(phone) || phone.length() < 11) {
                et_phone.requestFocus();
                Toast.makeText(this, "手机号码不正确", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void radioGroupFn(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.rb_male:
                tv_textView5.setText("你是个真男人");
                break;
            case R.id.rb_women:
                tv_textView5.setText("你是个女人");
                break;

        }
    }

    private void clickFn(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.ck_status:
                String desc1 = String.format("Switch按钮的状态是%s", isChecked ? "开" : "关");
                tv_result.setText(desc1);
                break;
            case R.id.rg_gender:
                break;

        }

    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        /*
//        buttonView代表被点击控件的本身，isChecked代表状态
//         */
//        // 通过onCheckedChanged 改变选择框背景
//        checkbox_custom.setBackgroundResource(isChecked? R.drawable.img_delete:R.drawable.ic_baseline_manage_search_24);
//        // 获取文本内容
//        String text = checkbox_custom.getText().toString();
//        Log.i("tag",text);
//        // 改变文本
//        String desc = String.format("您%s了这个CheckBox",isChecked?"勾选":"取消勾选");
//        checkbox_custom.setText(desc);
//
//
//    }

    // 当到达指定长度后，自动隐藏输入框
    public class HideTextWatcher implements TextWatcher {
        private final EditText mView;
        private int mMaxLength;

        public HideTextWatcher(EditText v, int maxLength) {
            this.mView = v;
            this.mMaxLength = maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // 获取已输入的文本字符串
            String str = editable.toString();
            // 输入文本达到11位（如手机号），或者达到6位（如登录密码）时关闭输入法
            if (str.length() == mMaxLength) {
                Utils.hideOneInputMethod(DrawableStateActivity.this, mView);
            }
        }
    }
}
