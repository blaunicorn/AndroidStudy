package com.example.chapter06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter06.database.ShoppingDBHelper;
import com.example.chapter06.entity.GoodsInfo;
import com.example.chapter06.utils.Utils;

import org.w3c.dom.Text;

public class ShoppingDetailActivity extends AppCompatActivity {

    private TextView tv_title;
    private TextView tv_count;
    private TextView tv_goods_price;
    private TextView tv_goods_desc;
    private ImageView iv_goods_pic;
    private ShoppingDBHelper mShoppingDBHelper;
    private int mGoodsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_detail);

        tv_title = findViewById(R.id.tv_title);
        tv_count = findViewById(R.id.tv_count);
        tv_goods_price = findViewById(R.id.tv_goods_price);
        tv_goods_desc = findViewById(R.id.tv_goods_desc);
        iv_goods_pic = findViewById(R.id.iv_goods_pic);

        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));

        mShoppingDBHelper = ShoppingDBHelper.getInstance(this);

        //  设置按钮监听
        findViewById(R.id.iv_back).setOnClickListener(this::clickFn);
        findViewById(R.id.iv_cart).setOnClickListener(this::clickFn);
        findViewById(R.id.btn_add_cart).setOnClickListener(this::clickFn);
    }

    private void clickFn(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_cart:
                Intent intent = new Intent(this,ShoppingCartActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_add_cart:
                addToCart(mGoodsId);
                break;
        }
    }

    private void addToCart(int goods_id) {
        mShoppingDBHelper.insertCartInfo(goods_id);
//        更新购物车中商品的数量
        showCartInfoTotal();
        Utils.show(this,"已添加一部到购物车");
    }
    // 查询购物车商品总数，并展示
    private void showCartInfoTotal() {
        int count = mShoppingDBHelper.countCartInfo();
        MyApplication.getInstance().goodsCount = count;
        tv_count.setText(String.valueOf(count));
    }
    @Override
    protected void onResume() {
        super.onResume();
        showDetail();
    }

    // 展示商品的详细信息
    private void showDetail() {
//        获取上一个页面传来的商品编号
        mGoodsId = getIntent().getIntExtra("goods_id", 0);
        if (mGoodsId >0) {
            // 根据商品编号查询商品数据库中的商品记录
            GoodsInfo goodsInfo = mShoppingDBHelper.queryGoodsInfoById(mGoodsId);
            tv_title.setText(goodsInfo.name);
            tv_goods_desc.setText(goodsInfo.description);
            tv_goods_price.setText(String.valueOf((int) goodsInfo.price));
            iv_goods_pic.setImageURI(Uri.parse(goodsInfo.picPath));
        }
    }
}