package com.example.chapter08.entity;

// 购物车实体信息
public class CartInfo {
    public int id;
//     商品编号
    public int goodsId;
//    商品数量
    public int count;

    // 为了listview便于适配器操作，增加goodsInfo商品信息
    public GoodsInfo goodsInfo;

    public CartInfo() {

    }

    public CartInfo(int id, int goodsId, int count) {
        this.id = id;
        this.goodsId = goodsId;
        this.count = count;
        this.goodsInfo = new GoodsInfo();
    }

    @Override
    public String toString() {
        return "CartInfo{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", count=" + count +
                '}';
    }
}
