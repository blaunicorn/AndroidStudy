package com.example.chapter07_server.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.chapter07_server.database.UserDBHelper;

public class UserInfoProvider extends ContentProvider {
    private UserDBHelper dbHelper;

    public UserInfoProvider() {
    }

    // 如要使用id，需要构建匹配器
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    // 1代表多行，2代表单行
    private  static  final  int USERS =1;
    private  static  final  int USER =2;

    static  {
//        Uri指定了2条路径
        URI_MATCHER.addURI(UserInfoContent.AUTHORITIES, "/user",USERS);
        // #为通配符
        URI_MATCHER.addURI(UserInfoContent.AUTHORITIES, "/user/#",USER);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
//        在删除时就可以匹配，是多条数据，还是单条数据
        int count = 0;
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        switch (URI_MATCHER.match(uri)) {
            case USERS:
                // 表示 content://com.example.chapter07_serve..provider.UserInfoProvider/user/
                // 即可以删除多行

                count= database.delete(UserDBHelper.TABLE_NAME,selection,selectionArgs);
                database.close();
                break;
            case USER:
                // 表示 content://com.example.chapter07_serve..provider.UserInfoProvider/user/2
                // 即 匹配 条目id为2的单个用户
                String id = uri.getLastPathSegment();
                database.delete(UserDBHelper.TABLE_NAME,"_id=?",new String[]{id});
                dbHelper.close();
                break;
        }

        return count;
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    // 请求方式 content://com.example.chapter07_serve..provider.UserInfoProvider/user/1
    //  通用前缀 授权者名称（保证唯一性） 数据路径（用来确定请求的是哪个数据集） id:数据编号，用来请求单条数据，如果是多条数据可忽略
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.

//        这里需要强制匹配，防止随意传参，访问就更加安全
        if (URI_MATCHER.match(uri)!=USERS) {
            // 不匹配，直接返回吧！
            return null;
        }
        // 通过dbHelper 实例化 读取sql数据库,然后在表中插入数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(UserDBHelper.TABLE_NAME,null,values);
        // 通过资源标识，content://authority/data_path/id
        return uri;
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        Log.d("wcy","onCreate");
        dbHelper = UserDBHelper.getInstance(getContext());
//        return false;
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Log.d("wcy","onQuery");
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        // 得到一个cursor，然后将它返回
        return readableDatabase.query(UserDBHelper.TABLE_NAME, projection, selection,
                selectionArgs, null, null, null);

//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}