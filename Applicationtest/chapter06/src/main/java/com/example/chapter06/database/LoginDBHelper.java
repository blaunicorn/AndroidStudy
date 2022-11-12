package com.example.chapter06.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.chapter06.entity.LoginInfo;
import com.example.chapter06.entity.User;

import java.util.ArrayList;
import java.util.List;

public class LoginDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "login.db";
    private static final String TABLE_NAME = "login_info";
    private static final int DB_VERSION = 1;
    private static LoginDBHelper mHelper = null;
    //    定义读写函数，使读写分离
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    // 调用父类方法，并向父类方法内传参
    private LoginDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    //    利用单例模式获取数据库帮助器的唯一实例
    public static LoginDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new LoginDBHelper(context);
        }
        return mHelper;
    }

    //    打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    //    打开数据库的写连接
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

    //    创建数据库和数据表
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL," +
                " phone VARCHAR NOT NULL," +
                " password VARCHAR NOT NULL," +
                "remember INTEGER NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }

    //    数据库版本更新时的操作
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//当版本升级时，执行优化逻辑
        // eg 版本升级后，增加两个字段
        String sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN  phone VARCHAR;";
        mRDB.execSQL(sql);
        sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN  password VARCHAR;";
        mRDB.execSQL(sql);
    }

    public long insert(LoginInfo info) {
        ContentValues values = new ContentValues();
        values.put("phone", info.phone);
        values.put("password", info.password);
        values.put("remember", info.remember);
        return mWDB.insert(TABLE_NAME, null, values);
    }

    public void save(LoginInfo info) {
        // 逻辑简单化：如果原来有，就更新，如果原来没有，就新增。
//        事务操作，有错误会回滚
        Log.d("wcy", "save: 1111"+ info.toString());
        try {
            mWDB.beginTransaction();
            delete(info);
            insert(info);
            mWDB.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mWDB.endTransaction();
        }
    }

    public long delete(LoginInfo info) {
        return mWDB.delete(TABLE_NAME, "phone=?", new String[]{info.phone});
    }

    //    读取最后一条登录的手机号和密码
    public LoginInfo queryTop() {
        Log.d("wcy", "queryTop:1 ");
        LoginInfo info = new LoginInfo();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE remember = 1 ORDER BY id DESC limit 1";
        Cursor cursor = mRDB.rawQuery(sql, null);
        //   Cursor  cursor = mRDB.query(TABLE_NAME,null,"phone=?",new String[]{},null,null,null);
        if (cursor.moveToNext()) {
            Log.d("wcy", "queryTop: 2.1");
            info.id = cursor.getInt(0);
            info.phone = cursor.getString(1);
            info.password = cursor.getString(2);
            info.remember = (cursor.getInt(3) == 0) ? false : true;
            Log.d("wcy", "queryTop: 3"+info.toString());
        }

        return info;
    }

    // 通过phone获取用户信息
    public  LoginInfo queryByPhone(String phone) {
        LoginInfo info = new LoginInfo();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE phone = " + phone  + " and remember=1 ORDER BY id DESC limit 1";
        Cursor cursor = mRDB.rawQuery(sql, null);
        //   Cursor  cursor = mRDB.query(TABLE_NAME,null,"phone=?",new String[]{},null,null,null);
        if (cursor.moveToNext()) {
             info.id = cursor.getInt(0);
            info.phone = cursor.getString(1);
            info.password = cursor.getString(2);
            info.remember = (cursor.getInt(3) == 0) ? false : true;
         }
        return info;
    }


    //    提供删除的方法
    public long deleteByName(String name) {
        //  数组便于扩展 name=? and and age=?  new String[]{"zhangsan",12}
//        删除所有 mWDB.delete(TABLE_NAME,“1=1”,null);
        return mWDB.delete(TABLE_NAME, "name=?", new String[]{name});
    }

    //    修改更新的方法
    public long update(User user) {
        ContentValues values = new ContentValues();
        values.put("name", user.name);
        values.put("age", user.age);
        values.put("height", user.height);
        values.put("weight", user.weight);
        values.put("married", user.married);
        return mWDB.update(TABLE_NAME, values, "name=?", new String[]{user.name});
    }

    //    查询所有
    public List<User> queryAll() {
        List<User> list = new ArrayList<>();
//        执行记录查询动作，返回结果集的游标
        Cursor cursor = mRDB.query(TABLE_NAME, null, null, null, null, null, null);
//       循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            User user = new User();
            user.id = cursor.getInt(0);
            user.name = cursor.getString(1);
            user.age = cursor.getInt(2);
            user.height = cursor.getLong(3);
            user.weight = cursor.getFloat(4);
//            sqlite没有布尔型，用0表示false，1表示true
            user.married = (cursor.getInt(5) == 0) ? false : true;
            list.add(user);
        }
        return list;
    }

    //    查询单个
    public List<User> queryByName(String name) {
        List<User> list = new ArrayList<>();
        Cursor cursor = mRDB.query(TABLE_NAME, null, "name like ?", new String[]{"%" + name + "%"}, null, null, null);
        while (cursor.moveToNext()) {
            User user = new User();
            user.id = cursor.getInt(0);
            user.name = cursor.getString(1);
            user.age = cursor.getInt(2);
            user.height = cursor.getLong(3);
            user.weight = cursor.getFloat(4);
//            sqlite没有布尔型，用0表示false，1表示true
            user.married = (cursor.getInt(5) == 0) ? false : true;
            list.add(user);
        }

//        ps:事务管理
        try {
            mWDB.beginTransaction();
//                    事务1  mWDB.insert(TABLE_NAME, null, values1)
//                            事务2 mWDB.insert(TABLE_NAME, null, values2)
//                           如果两个事务不是同时成功，则回滚，避免有同时包含增加减少等互补事务进程失败
            mWDB.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mWDB.endTransaction();
        }

        return list;
    }


}

