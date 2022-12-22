package com.example.chapter07_client;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.chapter07_client.entity.ImageInfo;
import com.example.chapter07_client.utils.PermissionUtils;
import com.example.chapter07_client.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProviderMmsActivity extends AppCompatActivity {
    // 定义一个全局的图片列表参数
    private List<ImageInfo> mImageList = new ArrayList<>();
    private GridLayout gl_appendix;
    private EditText et_message;
    private EditText et_title;
    private EditText et_phone;
    private Uri picUir;

    private static final String[] PERMISSIONS = new String[] {
           Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_mms);
        // 程序目的：自动加载图片，并添加到界面内发送

        et_phone = findViewById(R.id.et_phone);
        et_title = findViewById(R.id.et_title);
        et_message = findViewById(R.id.et_message);
        gl_appendix = findViewById(R.id.gl_appendix);
        // 1.5 为防止图片没有添加入 手机数据库，先手动让MediaStore扫描入库.  四个参数：上下文、扫描路径、扫描类型、callback函数
        MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String s, Uri uri) {
//                        也可以扫描完成再去请求权限
                    }
                });

//        1.4 引发权限判断弹框.如果没有授权，请求授权；如果已经授权了，if里是true，就直接调用下一步的逻辑。
          if   (PermissionUtils.checkPermission(this,PERMISSIONS ,PERMISSION_REQUEST_CODE)) {};
        // 1.1 加载图片列表
        loadImageList();
        // 1.2 把图片放在界面网格里显示
        showImageGrid();
    }

    //    1.3 增加动态请求权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       if (requestCode == PERMISSION_REQUEST_CODE && PermissionUtils.checkGrant(grantResults)) {
           // 1.1 加载图片列表
           loadImageList();
           // 1.2 把图片放在界面网格里显示
           showImageGrid();
       }
    }

    private void showImageGrid() {
//        1.2.1清空
        gl_appendix.removeAllViews();
        for (ImageInfo imageInfo : mImageList) {
//           1.2.2 把image转成ImageView控件，再把控件添加到网格布局
            ImageView iv_appendix = new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeFile(imageInfo.path);
            iv_appendix.setImageBitmap(bitmap);
            iv_appendix.setScaleType(ImageView.ScaleType.FIT_CENTER);
            int px = Utils.dip2px(this,110);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(px,px);
            iv_appendix.setLayoutParams(params);
            int padding = Utils.dip2px(this,5);
            iv_appendix.setPadding(padding,padding,padding,padding);
            iv_appendix.setOnClickListener(v-> {
                Log.d("wcy", "showImageGrid: ");
//                1.6 发送彩信
                sendMms(et_phone.getText().toString(), et_title.getText().toString(), et_message.getText().toString(), imageInfo.path);
              // 1.7在xml中创建FileProvider 继承于ContentProvider 对第三方应用（譬如彩信）暴露文件，并授予文件读写操作的权限
//            <provider
//                android:grantUriPermissions="true"
//                android:name="androidx.core.content.FileProvider"
//                android:authorities="com.example.chapter07_client.provider.fileProvider" />
            });
            gl_appendix.addView(iv_appendix);
//           1.2.3 在Manifest.xml中增加读取文件的权限，并动态请求     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"></uses-permission>
        }
    }

    @SuppressLint("Range")
    private void loadImageList() {
//        通过 MediaStore 查询
        // 构建查询的条件:编号  名称 大小 路径，尺寸在300k以内,并只要6张
        String[] columns = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATA
        };
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, "_size<307200", null, "_size DESC");

        // 构建ImageInfo实体类，便于操作

        int count = 0;
        if (cursor != null) {
            while (cursor.moveToNext() && count < 6) {
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                imageInfo.name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
                imageInfo.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                imageInfo.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                count++;
                mImageList.add(imageInfo);
                Log.d("wcy", "loadImageList: "+ imageInfo.toString());
            }
        }
    }

    // 1.6 发送带图片的彩信
    private void sendMms(String phone, String title, String message,String path) {
//       1.8根据指定路径构建一个Uri对象
        Uri uri = Uri.parse(path);
//        兼容Android7.0 把访问文件的Uri方法改成FileProvider
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            // 通过FileProvider 获得文件的Uri访问方式
           uri = FileProvider.getUriForFile(this,"Download",new File(path));
          Log.d("wcy","new uri:"+ uri);
        }

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
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        // 附件流的类型为图片
        intent.setType("image/*");
        // 跳转，因为未指定要打开哪个页面，所以系统会在底部弹出选择窗口
        startActivity(intent);
        Utils.show(this,"请在弹窗中选择短信或信息应用");
    }
}