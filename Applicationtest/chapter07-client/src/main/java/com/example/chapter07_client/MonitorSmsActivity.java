package com.example.chapter07_client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MonitorSmsActivity extends AppCompatActivity {

    private SmsGetObserver mObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_sms);

        // 注册短信内容观察器，一旦发生数据变化，就触发观察器的onChange方法
//        短信的Uri为：
        Uri uri = Uri.parse("content://sms");
        mObserver = new SmsGetObserver(this);
        getContentResolver().registerContentObserver(uri, true, mObserver);
    }

    // 短信内容发生变化，执行回调函数
    private static class SmsGetObserver extends ContentObserver {


        private final Context mContext;

        public SmsGetObserver(Context context) {
            super(new Handler(Looper.getMainLooper()));
            this.mContext = context;
        }

        @Override
        public void onChange(boolean selfChange, @Nullable Uri uri) {
            Log.d("wcy", "onChange: "+uri.toString());
            super.onChange(selfChange, uri);
//            onChange会调用多次，收到一条短信会调用两次
//                    第一次 mUri  content://sms/raw//20
//            第二次mUri  content://sms/inbox/20
//            安卓7.0以上系统，点击标记为已读时，也会调用一次
//                    mUri content://sms
//            收到的每条短信都是uri后面带一个确定的数据，对应数据库的_id，比如上面的数字20
            // 所以只监听第二次的短信
            if (uri == null) {
                return;
            }
            if (!uri.toString().contains("content://sms/raw") || uri.toString().equals("content://sms")) {
                Log.d("wcy", "onChange: uri");
                return;
            }
            uri= Uri.parse("content://sms/raw");
            Log.d("wcy", "onChange: uri jump"+ uri.toString());
            // 通过内容解析器获取符合条件的结果集游标
            Cursor cursor = mContext.getContentResolver().query(uri, new String[]{"address", "body", "date"}, null, null, "date DESC");
            Log.d("wcy", "onChange: cursor");
            if (cursor.moveToNext()) {
                Log.d("wcy", "onChange: cursor");
                // 短信的发送号码
                @SuppressLint("Range") String sender = cursor.getString(cursor.getColumnIndex("address"));
//                 短信的内容
                @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("body"));
                Log.d("wcy",String.format("sender:%s,content:%s",sender,content));

            }
            cursor.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 不需要一直接听，app关闭时，取消监听
        getContentResolver().unregisterContentObserver(mObserver);
    }
}