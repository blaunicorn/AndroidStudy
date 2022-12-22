package com.example.chapter08;

import android.app.Application;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;


import com.example.chapter08.database.ShoppingDBHelper;
import com.example.chapter08.entity.GoodsInfo;
import com.example.chapter08.utils.SharedUtils;
import com.example.chapter08.utils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class MyApplication extends Application {
    //    可以全局设置参数,存储在内存中
//    声明一个实例，并获取。不用new 是因为 android会自动new
    private static MyApplication myApplication;
    // 声明一个公共的信息映射对象，可当做全局变量
    public HashMap<String, String> infoMap = new HashMap<>();



    // 定义一个购物车中的商品总数量
    public  int  goodsCount=0;

    public static MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("wcy", "onCreate: Application");
        myApplication = this;

        // 模拟初始化商品信息
        initGoodsInfo();
    }

    private void initGoodsInfo() {
//获取共享参数保存的是否是首次打开参数
        boolean isFirst = SharedUtils.getInstance(this).readBoolean("first", true);
        if (isFirst) {
            // 模拟网络图片下载，先从本地存入私有地址
            List<GoodsInfo> list = GoodsInfo.getDefaultList();
            for (GoodsInfo info : list) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), info.pic_id);
                // 获取当前app的所有下载路径
                String directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separatorChar;
                String path = directory + info.pic_id + ".png";
                Log.d("wcy", path);
                // 存储卡上保存商品图片
                Utils.saveImage(path, bitmap);
                info.picPath = path;
                // 回收位图对象
                bitmap.recycle();
            }
            // 打开数据库，把商品信息插入到表中.首先获取数据库服务类实例，打开数据库、插入数据、关闭数据库
            ShoppingDBHelper dbHelper = ShoppingDBHelper.getInstance(this);
            dbHelper.openWriteLink();
            dbHelper.insertGoodsInfoList(list);
            dbHelper.closeLink();
//              把是否是首次打开写入共享参数，把true 改成false
            SharedUtils.getInstance(this).writeBoolean("first", false);
        }
    }

    // 应用终止时调用
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    // 在配置改变时调用。例如从横屏转成竖屏
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }



}
