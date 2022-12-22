package com.example.chapter08.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chapter08.entity.CartInfo;
import com.example.chapter08.entity.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

public class ShoppingDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "shopping.db";
    private static final String TABLE_GOODS_INFO = "goods_info";
    private static final String TABLE_CART_INFO = "cart_info";
    private static final String TABLE_NAME = "cart_info";
    private static final int DB_VERSION = 1;
    private static ShoppingDBHelper mHelper = null;
    //    定义读写函数，使读写分离
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    // 调用父类方法，并向父类方法内传参
    private ShoppingDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    //    利用单例模式获取数据库帮助器的唯一实例
    public static ShoppingDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new ShoppingDBHelper(context);
        }
        return mHelper;
    }

    //    创建数据库和数据表
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 创建商品信息表
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_GOODS_INFO + " (" +
                "_id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL," +
                " name VARCHAR NOT NULL," +
                " description VARCHAR NOT NULL," +
                " price FLOAT NOT NULL," +
                "pic_path VARCHAR NOT NULL);";
        sqLiteDatabase.execSQL(sql);
        // 购物车信息表
        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_CART_INFO + " (" +
                "_id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL," +
                " goods_id INTEGER NOT NULL," +
                " count INTEGER NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(sql);
    }


    //    数据库版本更新时的操作
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//当版本升级时，执行优化逻辑
        // eg 版本升级后，增加两个字段
//        String sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN  phone VARCHAR;";
//        mRDB.execSQL(sql);
//        sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN  password VARCHAR;";
//        mRDB.execSQL(sql);
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

    // 添加多条商品信息
    public void insertGoodsInfoList(List<GoodsInfo> list) {
        try {
            mWDB.beginTransaction();
            for (GoodsInfo info : list) {
                ContentValues values = new ContentValues();
                values.put("name", info.name);
                values.put("description", info.description);
                values.put("price", info.price);
                values.put("pic_path", info.picPath);
                mWDB.insert(TABLE_GOODS_INFO, null, values);
            }
            mWDB.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mWDB.endTransaction();
        }
    }

    public long insert(String TABLE_NAME, GoodsInfo info) {
        ContentValues values = new ContentValues();
        values.put("name", info.name);
        values.put("description", info.description);
        values.put("price", info.price);
        values.put("pic_path", info.picPath);
        return mWDB.insert(TABLE_NAME, null, values);
    }

    //    public void save(LoginInfo info) {
//        // 逻辑简单化：如果原来有，就更新，如果原来没有，就新增。
////        事务操作，有错误会回滚
//        Log.d("wcy", "save: 1111"+ info.toString());
//        try {
//            mWDB.beginTransaction();
//            delete(info);
//            insert(info);
//            mWDB.setTransactionSuccessful();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            mWDB.endTransaction();
//        }
//    }
//
//    public long delete(LoginInfo info) {
//        return mWDB.delete(TABLE_NAME, "phone=?", new String[]{info.phone});
//    }
//
//    //    读取最后一条登录的手机号和密码
//    public LoginInfo queryTop() {
//        Log.d("wcy", "queryTop:1 ");
//        LoginInfo info = new LoginInfo();
//        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE remember = 1 ORDER BY id DESC limit 1";
//        Cursor cursor = mRDB.rawQuery(sql, null);
//        //   Cursor  cursor = mRDB.query(TABLE_NAME,null,"phone=?",new String[]{},null,null,null);
//        if (cursor.moveToNext()) {
//            Log.d("wcy", "queryTop: 2.1");
//            info.id = cursor.getInt(0);
//            info.phone = cursor.getString(1);
//            info.password = cursor.getString(2);
//            info.remember = (cursor.getInt(3) == 0) ? false : true;
//            Log.d("wcy", "queryTop: 3"+info.toString());
//        }
//
//        return info;
//    }
//
//    // 通过phone获取用户信息
//    public  LoginInfo queryByPhone(String phone) {
//        LoginInfo info = new LoginInfo();
//        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE phone = " + phone  + " and remember=1 ORDER BY id DESC limit 1";
//        Cursor cursor = mRDB.rawQuery(sql, null);
//        //   Cursor  cursor = mRDB.query(TABLE_NAME,null,"phone=?",new String[]{},null,null,null);
//        if (cursor.moveToNext()) {
//            info.id = cursor.getInt(0);
//            info.phone = cursor.getString(1);
//            info.password = cursor.getString(2);
//            info.remember = (cursor.getInt(3) == 0) ? false : true;
//        }
//        return info;
//    }
//
//
//    //    提供删除的方法
//    public long deleteByName(String name) {
//        //  数组便于扩展 name=? and and age=?  new String[]{"zhangsan",12}
////        删除所有 mWDB.delete(TABLE_NAME,“1=1”,null);
//        return mWDB.delete(TABLE_NAME, "name=?", new String[]{name});
//    }
//
//    //    修改更新的方法
//    public long update(User user) {
//        ContentValues values = new ContentValues();
//        values.put("name", user.name);
//        values.put("age", user.age);
//        values.put("height", user.height);
//        values.put("weight", user.weight);
//        values.put("married", user.married);
//        return mWDB.update(TABLE_NAME, values, "name=?", new String[]{user.name});
//    }
//
//    //    查询所有
    public List<GoodsInfo> queryAll() {
        List<GoodsInfo> list = new ArrayList<>();
//        执行记录查询动作，返回结果集的游标
        Cursor cursor = mRDB.query(TABLE_GOODS_INFO, null, null, null, null, null, null);
//       循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.id = cursor.getInt(0);
            goodsInfo.name = cursor.getString(1);
            goodsInfo.description = cursor.getString(2);
            goodsInfo.price = cursor.getFloat(3);
            goodsInfo.picPath = cursor.getString(4);
            list.add(goodsInfo);
//            Log.d("wcy", "queryAll: "+goodsInfo.toString());
        }
        cursor.close();
        return list;
    }

    // 添加商品到购物车
    public void insertCartInfo(int goodsId) {
        // 如果购物车里不存在该商品，添加一条信息
        CartInfo cartInfo = queryCartInfoByGoodsId(goodsId);
        ContentValues values = new ContentValues();
        values.put("goods_id", goodsId);
        if (cartInfo == null) {
            values.put("count", 1);
            mWDB.insert(TABLE_CART_INFO, null, values);
        } else {
            values.put("_id", cartInfo.id);
            values.put("count", cartInfo.count + 1);
            mWDB.update(TABLE_CART_INFO, values, "_id=?", new String[]{String.valueOf(cartInfo.id)});
        }

//        如果购物车中存在该商品，该商品数量加1
    }

    // 根据商品信息ID查询购物车信息
    private CartInfo queryCartInfoByGoodsId(int goodsId) {
        Cursor cursor = mRDB.query(TABLE_CART_INFO, null, "goods_id=?", new String[]{String.valueOf(goodsId)}, null, null, null);
        CartInfo cartInfo = null;
        if (cursor.moveToNext()) {
            cartInfo = new CartInfo();
            cartInfo.id = cursor.getInt(0);
            cartInfo.goodsId = cursor.getInt(1);
            cartInfo.count = cursor.getInt(2);
        }
        return cartInfo;

    }

    // 查询购物车商品总数，
    public int countCartInfo() {
        int count = 0;
        String sql = "SELECT SUM(count) FROM " + TABLE_CART_INFO;
        Cursor cursor = mRDB.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        return count;
    }

    // 查询购物车中所有的信息列表
    public List<CartInfo> queryAllCartInfo() {
        List<CartInfo> list = new ArrayList<>();

        Cursor cursor = mRDB.query(TABLE_CART_INFO, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            CartInfo cartInfo = new CartInfo();
            cartInfo.id = cursor.getInt(0);
            cartInfo.goodsId = cursor.getInt(1);
            cartInfo.count = cursor.getInt(2);
            list.add(cartInfo);
        }
        return list;
    }

    public GoodsInfo queryGoodsInfoById(int goodsId) {
        GoodsInfo goodsInfo = null;
        // Log.d("wcy", "queryGoodsInfoById: "+ goodsId);
        Cursor cursor = mRDB.query(TABLE_GOODS_INFO, null, "_id=?", new String[]{String.valueOf(goodsId)}, null, null, null);
        if (cursor.moveToNext()) {
            goodsInfo = new GoodsInfo();
            goodsInfo.id = cursor.getInt(0);
            goodsInfo.name = cursor.getString(1);
            goodsInfo.description = cursor.getString(2);
            goodsInfo.price = cursor.getFloat(3);
            goodsInfo.picPath = cursor.getString(4);
            // .d("wcy", "queryGoodsInfoById: "+goodsInfo.toString());
        }
        return goodsInfo;

    }

    public void deleteCartInfoByGoodsId(int goodsId) {
        mWDB.delete(TABLE_CART_INFO, "goods_id=?", new String[]{String.valueOf(goodsId)});
    }

    public void deleteAllCartInfo() {
        mWDB.delete(TABLE_CART_INFO, "1=1", null);
    }
//
//    //    查询单个
//    public List<User> queryByName(String name) {
//        List<User> list = new ArrayList<>();
//        Cursor cursor = mRDB.query(TABLE_NAME, null, "name like ?", new String[]{"%" + name + "%"}, null, null, null);
//        while (cursor.moveToNext()) {
//            User user = new User();
//            user.id = cursor.getInt(0);
//            user.name = cursor.getString(1);
//            user.age = cursor.getInt(2);
//            user.height = cursor.getLong(3);
//            user.weight = cursor.getFloat(4);
////            sqlite没有布尔型，用0表示false，1表示true
//            user.married = (cursor.getInt(5) == 0) ? false : true;
//            list.add(user);
//        }
//
////        ps:事务管理
//        try {
//            mWDB.beginTransaction();
////                    事务1  mWDB.insert(TABLE_NAME, null, values1)
////                            事务2 mWDB.insert(TABLE_NAME, null, values2)
////                           如果两个事务不是同时成功，则回滚，避免有同时包含增加减少等互补事务进程失败
//            mWDB.setTransactionSuccessful();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            mWDB.endTransaction();
//        }
//
//        return list;
//    }

}
