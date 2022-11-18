package com.example.chapter06.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.chapter06.entity.BookInfo;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
//            ... 为可变参数
    void insert(BookInfo... book);

    @Delete
    void delete(BookInfo... book);

    // 删除所有
    @Query("DELETE FROM BookInfo")
    void deleteAll();

    @Update
    int update (BookInfo... book);

    //  自定义的要自己写语句,写成类名一致也可以，sql不区分大小写
    @Query("SELECT * FROM BookInfo")
    List<BookInfo> queryAll();


    // 根据姓名查询数据
    @Query("SELECT * FROM BookInfo WHERE name = :name ORDER BY id DESC limit 1")
    BookInfo queryByName(String name);
}
