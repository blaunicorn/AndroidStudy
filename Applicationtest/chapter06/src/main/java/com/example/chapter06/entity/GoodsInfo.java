package com.example.chapter06.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.chapter06.R;

import java.util.ArrayList;

// 利用room 创建实体
@Entity
public class GoodsInfo {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String description;
    public float price;
    // 图片路径
    public String picPath;
    //    图片资源编号
    public int pic_id;

    // 模拟一个手机商品的数组
    private static String[] mNameArray = {
            "iphone01", "iphone02",
            "iphone03", "iphone04", "iphone05", "iphone06",
            "iphone07", "iphone08",
    };
    //    模拟一个手机描述数组
    private static String[] mDescArray = {
            "苹果1", "苹果2",
            "苹果03", "苹果04", "苹果05", "苹果06",
            "i苹果07", "苹果08",
    };
    //    模拟一个手机价格数组
    private static float[] mPriceArray = {
            6299, 4999,
            9333, 8999, 7999, 2999,
            1999, 999,
    };
    //    模拟一个手机图片路径
    private static int[] mPicArray = {
            R.drawable.phone1, R.drawable.phone2,
            R.drawable.phone3, R.drawable.phone4, R.drawable.phone5,
            R.drawable.phone6, R.drawable.phone7, R.drawable.phone8
    };

    // 获取默认的手机信息列表
    public static ArrayList<GoodsInfo> getDefaultList() {
        ArrayList<GoodsInfo> goodsInfoList = new ArrayList<>();
        for (int i = 0; i < mNameArray.length; i++) {
            GoodsInfo info = new GoodsInfo();
            info.id = i;
            info.name = mNameArray[i];
            info.description = mDescArray[i];
            info.price = mPriceArray[i];
            info.pic_id = mPicArray[i];
            goodsInfoList.add(info);
        }

        return goodsInfoList;
    }
    public GoodsInfo() {

    }

    public GoodsInfo( String name, String description, float price, String picPath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.picPath = picPath;

    }

    @Override
    public String toString() {
        return "GoodsInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", picPath='" + picPath + '\'' +
                '}';
    }
}
