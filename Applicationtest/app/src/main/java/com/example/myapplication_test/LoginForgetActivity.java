package com.example.myapplication_test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class LoginForgetActivity extends AppCompatActivity {

    private String mPhone;
    private String mVerifyCode;
    private TextView et_new_password_again;
    private TextView et_new_password;
    private TextView et_verify_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);

        // 从上一个页面获取手机号码
        mPhone = getIntent().getStringExtra("phone");

        findViewById(R.id.btn_verify_code).setOnClickListener(this::clickFn);
        findViewById(R.id.btn_confirm).setOnClickListener(this::clickFn);

        et_new_password = findViewById(R.id.et_new_password);
        et_new_password_again = findViewById(R.id.et_new_password_again);
        et_verify_code =findViewById((R.id.et_verify_code));
    }
    private void clickFn(View view) {
        switch (view.getId()) {
            case R.id.btn_verify_code:
                mVerifyCode = String.format("%06d",new Random().nextInt(999999));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请记住验证码");
                builder.setMessage("手机号"+mPhone+"，本次验证码是："+mVerifyCode +"。");
                builder.setPositiveButton("好的",null);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.btn_confirm:
//                点击了确定按钮
                if (et_new_password.getText().toString()==null || et_new_password_again.getText().toString() == null ||et_verify_code.getText().toString() == null ) {
                    Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_new_password.getText().toString().length()<6) {
                    Toast.makeText(this, "密码长度要大于6", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!et_new_password.getText().toString().equals(et_new_password_again.getText().toString())) {
                    Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_verify_code.getText().toString().length()!=6) {
                    Toast.makeText(this, "验证码长度不对", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
//                把修改好的密码返回给上一个页面
                Intent intent = new Intent();
                intent.putExtra("new_password",et_new_password.getText().toString());
                setResult(Activity.RESULT_OK,intent);
//                结束当前页面
                finish();
                break;

        }
    }
}