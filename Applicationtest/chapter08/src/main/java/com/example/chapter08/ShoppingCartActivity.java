package com.example.chapter08;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter08.adapter.CartAdapter;
import com.example.chapter08.database.ShoppingDBHelper;
import com.example.chapter08.entity.CartInfo;
import com.example.chapter08.entity.GoodsInfo;
import com.example.chapter08.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private TextView tv_count;
    private TextView tv_title;
    private ListView lv_cart;
    private ShoppingDBHelper mShoppingDBHelper;
    private  CartAdapter cartAdapter;

    // 购物车中的商品信息列表
    private List<CartInfo> mCartList;

    // 声明一个根据商品编号查找商品信息的映射，把商品信息缓存起来，避免每次使用都去查询数据库
    private Map<Integer, GoodsInfo> mGoodsMap = new HashMap<>();
    private TextView tv_total_price;
    private LinearLayout ll_empty;
    private LinearLayout ll_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("购物车");

        tv_count = findViewById(R.id.tv_count);
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));

        // 总价
        tv_total_price = findViewById(R.id.tv_total_price);
        lv_cart = findViewById(R.id.lv_cart);
        ll_empty = findViewById(R.id.ll_empty);
        ll_content = findViewById(R.id.ll_content);

        mShoppingDBHelper = ShoppingDBHelper.getInstance(this);

        //返回按钮
        findViewById(R.id.iv_back).setOnClickListener(this::clickFn);
        findViewById(R.id.btn_clear).setOnClickListener(this::clickFn);
        findViewById(R.id.btn_settle).setOnClickListener(this::clickFn);
        findViewById(R.id.btn_shopping_channel).setOnClickListener(this::clickFn);

    }

    private void clickFn(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_clear:
                // 清空购物车数据
                mShoppingDBHelper.deleteAllCartInfo();
                showCart();
                showCount();
                Utils.show(this, "购物车已清空");
                break;
            case R.id.btn_settle:
                // 点击结算按钮
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("结算商品");
                builder.setMessage("暂未开通");
                builder.setPositiveButton("我知道了", null);
                builder.create().show();
                break;
            case R.id.btn_shopping_channel:
                // 跳转到商场页面
                Intent intent = new Intent(this, ShoppingChannelActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showCart();
        showCount();
    }

    // 展示购物车中的商品列表：移出所欲信息
    private void showCart() {
        // 先清空
        lv_cart.removeAllViews();

        //        商品条目是listview，希望设置条目的宽度是屏幕的一般，就要获取到屏幕的宽度，然后复制给view视图
        //       查询商品数据库中的所有商品记录
        mCartList = mShoppingDBHelper.queryAllCartInfo();
        Log.d("wcy", "CartInfoList: " + mCartList.toString());
        if (mCartList.size() == 0) {
            return;
        }
        // 查出商品的详细信息
        for (CartInfo cartInfo : mCartList) {
            Log.d("wcy", "cartInfo: " + cartInfo.toString());
            // 根据商品编号查询商品数据库中的商品记录；
            GoodsInfo goodsInfo = mShoppingDBHelper.queryGoodsInfoById(cartInfo.goodsId);
            mGoodsMap.put(cartInfo.goodsId, goodsInfo);
            cartInfo.goodsInfo = goodsInfo;
        }

//        创建一个cardInfo适配器
         cartAdapter = new CartAdapter(this, mCartList);
        //把适配器添加到视图
        lv_cart.setAdapter(cartAdapter);
//        添加子元素点击事件。点击商品行跳转到商品的详情页
        lv_cart.setOnItemClickListener(this);
//        给商品行添加长按事件，长按商品行就删除该商品
        lv_cart.setOnItemLongClickListener(this);

//        {
//
//            for (CartInfo cartInfo : mCartList)
//            Log.d("wcy", "cartInfo: " + cartInfo.toString());
//            // 根据商品编号查询商品数据库中的商品记录；
//            GoodsInfo goodsInfo = mShoppingDBHelper.queryGoodsInfoById(cartInfo.goodsId);
//            mGoodsMap.put(cartInfo.goodsId, goodsInfo);
//
//            // 获取布局文件item_cart.xml的跟视图，变成java对象
//            View view = LayoutInflater.from(this).inflate(R.layout.item_cart, null);
//
////          给控件设置内容。 图片、名称、数量、价格、总价
//            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
////          直接引用绝对路径方式
//            iv_thumb.setImageURI(Uri.parse(goodsInfo.picPath));
//
//            TextView tv_name = view.findViewById(R.id.tv_name);
//            tv_name.setText(goodsInfo.name);
//
//            TextView tv_desc = view.findViewById(R.id.tv_desc);
//            tv_desc.setText(goodsInfo.description);
//
//            TextView tv_count = view.findViewById(R.id.tv_count);
//            tv_count.setText(String.valueOf((int) cartInfo.count));
//
//            TextView tv_price = view.findViewById(R.id.tv_price);
//            tv_price.setText(String.valueOf((int) goodsInfo.price));
//            Log.d("wcy", "cartInfo: 2" + goodsInfo);
//            TextView tv_sum = view.findViewById(R.id.tv_sum);
//            tv_sum.setText(String.valueOf((int) goodsInfo.price * cartInfo.count));
//            Log.d("wcy", "cartInfo: 3" + cartInfo + "。 sum：" + String.valueOf((int) goodsInfo.price * cartInfo.count));
//
//
//
//
//
//            // 把布局添加到主页面
//            lv_cart.addView(view);
//        }

        // 重新计算购物车中的商品总金额
        refreshTotalPrice();
        showCount();
    }

    private void deleteGoods(CartInfo cartInfo) {
        //  从购物车数据库中删除商品
        mShoppingDBHelper.deleteCartInfoByGoodsId(cartInfo.goodsId);
//        列表中删除
//        CartInfo removed = null;
//        for (CartInfo info:mCartList) {
//            if (info.goodsId==cartInfo.goodsId) {
//              removed= cartInfo;
//            }
//        }
//        mCartList.remove(removed);
        mCartList.remove(cartInfo);
        Utils.show(this, "已从购物车删除" + mGoodsMap.get(cartInfo.goodsId).name);
        mGoodsMap.remove(cartInfo.goodsId);
        // 刷新
        refreshTotalPrice();
        showCount();
    }

    //  显示购物车图标中的商品数量
    private void showCount() {
        int count = mShoppingDBHelper.countCartInfo();
        MyApplication.getInstance().goodsCount = count;
        tv_count.setText(String.valueOf(count));
        // 如果购物车中没有商品，显示“空空”
        if (count == 0) {

            ll_empty.setVisibility(View.VISIBLE);

            ll_content.setVisibility(View.GONE);
//            lv_cart.removeAllViews();
//            刷新列表-》通知适配器发生了数据变化
            cartAdapter.notifyDataSetChanged();
        } else {
            findViewById(R.id.ll_empty).setVisibility(View.GONE);
            findViewById(R.id.ll_content).setVisibility(View.VISIBLE);
        }

    }

    private void refreshTotalPrice() {
        int totalPrice = 0;
        for (CartInfo cartInfo : mCartList) {
            GoodsInfo goodsInfo = mGoodsMap.get(cartInfo.goodsId);
            totalPrice += goodsInfo.price * cartInfo.count;
        }
        tv_total_price.setText(String.valueOf(totalPrice));
    }

    // 给商品行添加点击事件
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // 点击商品行，跳转到详情页
//        view.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingCartActivity.this, ShoppingDetailActivity.class);
            intent.putExtra("goods_id", mCartList.get(position).goodsId);
            startActivity(intent);
//        });
    }

    // 给商品行添加长按事件,弹出一个对话框
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        // 增加长按删除按钮
        //点击时，将商品名称和id传入
//        view.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
            builder.setMessage("是否从购物车中删除" + mCartList.get(position).goodsInfo.name + "?");
            builder.setPositiveButton("是", (dialog, which) -> {
                // 移除当前元素
                mCartList.remove(position);
                // 删除该商品
                deleteGoods(mCartList.get(position));
//                刷新列表-》通知适配器发生了数据裱花
                cartAdapter.notifyDataSetChanged();


            });
            builder.setNegativeButton("否", null);
            builder.create().show();
            return true;
//        });

//        return false;
    }
}