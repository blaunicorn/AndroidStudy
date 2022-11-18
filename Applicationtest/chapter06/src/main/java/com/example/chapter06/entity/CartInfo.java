package com.example.chapter06.entity;

// 购物车实体信息
public class CartInfo {
    public int id;
//     商品编号
    public int goodsId;
//    商品数量
    public int count;
    public CartInfo() {

    }

    public CartInfo(int id, int goodsId, int count) {
        this.id = id;
        this.goodsId = goodsId;
        this.count = count;
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
