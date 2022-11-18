package com.example.chapter06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chapter06.dao.BookDao;
import com.example.chapter06.entity.BookInfo;
import com.example.chapter06.utils.Utils;

import java.util.List;

public class RoomWriteActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_author;
    private EditText et_press;
    private EditText et_price;
    private TextView tv_books;
    private BookDao bookDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_write);

        et_name = findViewById(R.id.et_name);
        et_author = findViewById(R.id.et_author);
        et_press = findViewById(R.id.et_press);
        et_price = findViewById(R.id.et_price);
        tv_books = findViewById(R.id.tv_books);

         findViewById(R.id.btn_save).setOnClickListener((View.OnClickListener) this::ClickFn);
         findViewById(R.id.btn_delete).setOnClickListener((View.OnClickListener) this::ClickFn);
         findViewById(R.id.btn_update).setOnClickListener((View.OnClickListener) this::ClickFn);
         findViewById(R.id.btn_query_all).setOnClickListener((View.OnClickListener) this::ClickFn);
         findViewById(R.id.btn_query).setOnClickListener((View.OnClickListener) this::ClickFn);

         // 启动页面时，从app实例中获取数据库实例，并调用 唯一的书籍持久化对象，并设置为全局参数
        bookDao = MyApplication.getInstance().getBookDatabase().bookDao();
    }

    private void ClickFn(View view) {
        String name = et_name.getText().toString();
        String author = et_author.getText().toString();
        String press = et_press.getText().toString();
        String price = et_price.getText().toString();
        int id;
        switch (view.getId()) {
            case R.id.btn_save:
//                声明一个书籍信息对象，并填写它的各字段值
                BookInfo book = new BookInfo();
                book.setName(name);
                book.setAuthor(author);
                book.setPress(press);
                book.setPrice(Double.parseDouble(price));  // 转成double类型
                bookDao.insert(book);
                Utils.show(this,"添加成功");
                break;
            case R.id.btn_delete:
                BookInfo b2 = new BookInfo();
                b2 = bookDao.queryByName(name);
                if (b2!=null) {
                    bookDao.delete(b2); // 实际上是，根据id来删除
                    Utils.show(this,"删除成功");
                }
                Utils.show(this,"查无此人");
                break;
            case R.id.btn_update:
//                根据名字找到该条记录， 然后获取该条记录信息，然后修改之
                BookInfo b3 = new BookInfo();
                if (name ==null ) {
                    Utils.show(this,"名字不能为空");
                    return;
                }
                b3 = bookDao.queryByName(name);
                if (b3==null) {
                    Utils.show(this,"查无此人");
                    return;
                }
                b3.setName(name);
                b3.setAuthor(author);
                b3.setPress(press);
                b3.setPrice(Double.parseDouble(price));  // 转成double类型
                bookDao.update(b3);
                Utils.show(this,"更新成功");
                break;
                case R.id.btn_query:
//                    通过名字查询单条记录，然后把记录id存到起来
                    BookInfo b4 = new BookInfo();
                    b4 =bookDao.queryByName(name);
                    if (b4==null) {
                        Utils.show(this,"查无此人");
                        return;
                    }
                    id =  b4.getId();
                    et_name.setText(b4.getName());
                    et_author.setText(b4.getAuthor());
                    et_press.setText(b4.getPress());
                    et_price.setText(b4.getPrice().toString());
                    break;
            case R.id.btn_query_all:
                List<BookInfo> list = bookDao.queryAll();
                for (BookInfo bookInfo : list) {
                    Log.d("wcy", bookInfo.toString());
                }
                tv_books.setText(list.toString());

        }
    }
}