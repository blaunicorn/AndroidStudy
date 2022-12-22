package com.example.chapter02.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.example.chapter02.entity.BillInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class BillDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bill.db";
    // 账单信息表
    private static final String TABLE_BILL_INFO = "bills_info";
    private static final int DB_VERSION = 1;
    private static BillDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    private BillDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static BillDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new BillDBHelper(context);
        }
        return mHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (mWDB == null || !mWDB.isOpen()) {
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    // 关闭数据库连接
    public void closeLink() {
        if (mRDB != null && mRDB.isOpen()) {
            mRDB.close();
            mRDB = null;
        }
        if (mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }

    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 创建账单信息表
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_BILL_INFO +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " date VARCHAR NOT NULL," +
                " type INTEGER NOT NULL," +
                " amount DOUBLE NOT NULL," +
                " remark VARCHAR NOT NULL)      ;";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // 保存一条订单记录
    public  long save(BillInfo billInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("date",billInfo.date);
        contentValues.put("type",billInfo.type);
        contentValues.put("amount",billInfo.amount);
        contentValues.put("remark",billInfo.remark);
        return mWDB.insert(TABLE_BILL_INFO,null,contentValues);
    }

    // 根据月份查询数据
   @SuppressLint("Range")
   public List<BillInfo> queryByMonth(String yearMonth) {
        List<BillInfo> list = new ArrayList<>();

        // select * from bill_info where date like "2022-07%"
       String sql = "select * from " + TABLE_BILL_INFO + " where date like \"%" + yearMonth  + "%\"";
       Cursor cursor = mRDB.rawQuery(sql, null);
       while (cursor.moveToNext()) {
           BillInfo billInfo = new BillInfo();
           billInfo.id = cursor.getInt(cursor.getColumnIndex("_id"));
           billInfo.date = cursor.getString(cursor.getColumnIndex("date"));
           billInfo.type = cursor.getInt(cursor.getColumnIndex("type"));
           billInfo.amount = cursor.getDouble(cursor.getColumnIndex("amount"));
           billInfo.remark = cursor.getString(cursor.getColumnIndex("remark"));
           list.add(billInfo);
       }
       Log.d("wcy", "queryByMonth: "+yearMonth +":" + sql +":" + list);
       return  list;
   }

}
