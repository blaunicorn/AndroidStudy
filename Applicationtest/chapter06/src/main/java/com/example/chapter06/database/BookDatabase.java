package com.example.chapter06.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.chapter06.dao.BookDao;
import com.example.chapter06.entity.BookInfo;

// 利用Room框架创建并调用数据库
// entities表示该数据库有哪些表，version表示数据库的版本号；
// exportSchema 表示是否导出数据库信息的json串。需在build.gradle中指定导出路径
// exportSchema 表示是否导出数据库信息的json串。需在build.gradle中指定导出路径
@Database(entities = {BookInfo.class},version=1,exportSchema = true)
public abstract class BookDatabase extends RoomDatabase {
    // 获取该数据库中某张表的持久化对象
    public abstract BookDao bookDao();
}



