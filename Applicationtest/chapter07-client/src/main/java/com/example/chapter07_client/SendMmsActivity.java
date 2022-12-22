package com.example.chapter07_client;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter07_client.utils.Utils;

public class SendMmsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_appendix;
    private ActivityResultLauncher<Intent> intentActivityResultLauncher;
    private EditText et_message;
    private EditText et_title;
    private EditText et_phone;
    private Uri picUir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mms);

        et_phone = findViewById(R.id.et_phone);
        et_title = findViewById(R.id.et_title);
        et_message = findViewById(R.id.et_message);

        iv_appendix = findViewById(R.id.iv_appendix);
        iv_appendix.setOnClickListener(this);
        findViewById(R.id.btn_send_mms).setOnClickListener(this);
//        考虑到跳转并返回, 使用registerForActivityResult
        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {


            @Override
            public void onActivityResult(ActivityResult result) {
//                用户选择图片后，就到这里来了
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
//                    getData()返回的是图片地址Uri
                    picUir = intent.getData();
                    if (picUir != null) {
//                        把界面上的+号替换成图片
                        iv_appendix.setImageURI(picUir);
                        Log.d("wcy", "onActivityResult: picUri:" + picUir.toString());
                    } else {
                        Utils.show(SendMmsActivity.this, "没有选择图片");
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_appendix:
//                创建一个跳转到系统相册的意图，选择图片，并返回
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // 设置内容类型为图片类型
                intent.setType("image/*");
                // 打开系统相册，并等待图片选择结果
                intentActivityResultLauncher.launch(intent);
                break;
            case R.id.btn_send_mms:
                sendMms(et_phone.getText().toString(), et_title.getText().toString(), et_message.getText().toString());
                break;
        }
    }

    // 发送带图片的彩信
    private void sendMms(String phone, String title, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
//        在新的任务栈里发送彩信
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        设置接收方，允许读取携带的流文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

//         发送目标号码
        intent.putExtra("address", phone);
        // 发送彩信的标题
        intent.putExtra("subject", title);
        // 发送彩信的内容
        intent.putExtra("sms_body", message);
        // 发送彩信内的附件流
        intent.putExtra(Intent.EXTRA_STREAM, picUir);
        // 附件流的类型为图片
        intent.setType("image/*");
        // 跳转，因为未指定要打开哪个页面，所以系统会在底部弹出选择窗口
        startActivity(intent);
        Utils.show(this,"请在弹窗中选择短信或信息应用");
    }
}