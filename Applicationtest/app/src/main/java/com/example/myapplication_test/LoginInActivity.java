package com.example.myapplication_test;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication_test.utils.Utils;

import java.util.Random;

public class LoginInActivity extends AppCompatActivity {

    private TextView tv_password;
    private EditText et_password;
    private EditText et_phone;
    private TextView tv_phone;
    private Button btn_forget;
    private Button btn_login;
    private CheckBox ck_remember;
    private RadioButton rb_password;
    private RadioButton rb_verifycode;
    private ActivityResultLauncher<Intent> register;
    // 定义模拟密码和校验码
    private String mPassword = "111111";
    private String mVerifyCode = "222222";
    private String verifyCode;
    private SharedPreferences perferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        // 获取单选按钮，并设置监听
        RadioGroup rb_login = findViewById(R.id.rg_login);
        rb_login.setOnCheckedChangeListener(this::checkedChange);
        rb_password = findViewById(R.id.rb_password);
        rb_verifycode = findViewById(R.id.rb_verifycode);


        tv_password = findViewById(R.id.tv_password);
        tv_phone = findViewById(R.id.tv_phone);
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        btn_forget = findViewById(R.id.btn_forget);
        ck_remember = findViewById(R.id.ck_remember);
        btn_login = findViewById(R.id.btn_login);

        // 监听et_phone文本框文本位数，自动关闭软键盘
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
        et_password.addTextChangedListener(new HideTextWatcher(et_password, 6));

//        忘记密码按钮设置点击监听
        btn_forget.setOnClickListener(this::onClickFn);
        btn_login.setOnClickListener(this::onClickFn);

        // 保存在共享参数中
        perferences = getSharedPreferences("config", Context.MODE_PRIVATE);
//        启动时检测是否有缓存的账号密码
        reload();
        // 注册下一页面返回时使用的回调方法
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

            @Override
            public void onActivityResult(ActivityResult result) {
//                      从找回密码页面返回时，取出回调返回的数据
                if (result != null) {
                    Intent intent = result.getData();
                    if (intent != null && result.getResultCode() == Activity.RESULT_OK) {
                        Bundle bundle = intent.getExtras();
                        String new_password = bundle.getString("new_password");
                        mPassword = new_password;
                        String desc = String.format(" 收到响应消息：\n响应内容为%s", new_password);
                        Log.d(TAG, desc);
//                                    et_password.setText(desc);
                    }
                }
            }
        });
    }

    private void reload() {
        boolean isRemember = perferences.getBoolean("isRemember", false);
        if (isRemember) {
            String phone = perferences.getString("phone", "");
            et_phone.setText(phone);
            String password = perferences.getString("password", "");
            et_password.setText(password);

        }
        ck_remember.setChecked(isRemember);
    }

    private void onClickFn(View view) {
        String phone = et_phone.getText().toString();
        if (phone == null || phone.length() < 11) {
            Toast.makeText(this, "请输入正确位数的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
            case R.id.btn_forget:

                // 密码方式登录
                if (rb_password.isChecked()) {
//                    跳转到另一个页面，并携带手机号码
                    Intent intent = new Intent(this, LoginForgetActivity.class);
                    intent.putExtra("phone", phone);

                    register.launch(intent);
                    return;
                }
//                验证码校验登录
                if (rb_verifycode.isChecked()) {
                    // 生成6位随机的验证码，一般是后台生成并返给前端
                    verifyCode = String.format("%06d", new Random().nextInt(999999));
//                    弹出对话框，提示用户记住六位验证码
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("验证码为：");
                    builder.setMessage("手机号" + phone + "，本次验证码是：" + verifyCode);
                    builder.setPositiveButton("好的", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }
                break;
            // 登录按钮事件
            case R.id.btn_login:
                if (rb_password.isChecked()) {
                    if (!mPassword.equals((et_password.getText().toString()))) {
                        Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                    } else {
                        loginSuccess();
                    }
                    return;
                }
                // 验证码校验
                if (rb_verifycode.isChecked()) {
                    if (!mVerifyCode.equals(et_password.getText().toString())) {
                        Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        loginSuccess();
                    }

                }
                break;

        }
    }

    private void loginSuccess() {
//        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        String desc = String.format("您的手机号码是%s,恭喜你通过验证。", et_phone.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
        builder.setMessage(desc);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                // 结束当前页面
                finish();
            }
        });
        // 再看看 就不做处理了，直接关闭对话框
        builder.setNegativeButton("再看看", null);
        AlertDialog dialog = builder.create();
        dialog.show();

        if (ck_remember.isChecked()) {
            // 存入共享参数
            SharedPreferences.Editor editor = perferences.edit();
            editor.putString("phone", String.valueOf(et_phone.getText()));
            // 一般这里还需要加盐加密
            editor.putString("password", String.valueOf(et_password.getText()));
            editor.putBoolean("isRemember", ck_remember.isChecked());
            editor.commit();
        }


    }


    private void checkedChange(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            // 密码登录方式
            case R.id.rb_password:
                tv_password.setText(getString(R.string.login_password));
                et_password.setHint(getString(R.string.input_password));
                btn_forget.setText(getString(R.string.forget_password));
                ck_remember.setVisibility(View.VISIBLE);
                break;
            // 验证码登录方式
            case R.id.rb_verifycode:
                tv_password.setText(getString(R.string.verifycode));
                et_password.setHint(getString(R.string.input_verifycode));
                btn_forget.setText(getString(R.string.get_verifycode));
                ck_remember.setVisibility(View.GONE);
                break;
        }
    }

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
                Utils.hideOneInputMethod(LoginInActivity.this, mView);
            }
        }
    }
}