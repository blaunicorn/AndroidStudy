package com.example.chapter08.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter08.R;
import com.example.chapter08.ShoppingChannelActivity;
import com.example.chapter08.ShoppingDetailActivity;
import com.example.chapter08.entity.GoodsInfo;

import java.util.List;

public class GoodsAdapter extends BaseAdapter {

    // 设置传递上下文和集合
    private Context mContext;
    private List<GoodsInfo> mGoodsInfo;
    // 定义声明一个加入购物车的监听器对象
    private  AddCartListener mAddCartListener;

    // 新建构造方法
    public GoodsAdapter(Context mContext, List<GoodsInfo> mGoodsInfo,AddCartListener mAddCartListener) {
        this.mContext = mContext;
        this.mGoodsInfo = mGoodsInfo;
        this.mAddCartListener=mAddCartListener;
    }

    @Override
    public int getCount() {
        // 改成返回集合数量
        return mGoodsInfo.size();
    }

    @Override
    public Object getItem(int i) {
        return mGoodsInfo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        GoodsInfo goodsInfo = mGoodsInfo.get(i);

////    希望view 能否重用，所以设置缓存判断，第一次加载时， 当有view存在时加载时
        ViewHolder viewHolder = new ViewHolder();
        if (view == null) {
//      获取布局文件item_goods.xml的跟视图，并导入变成一个java对象
            view = LayoutInflater.from(mContext).inflate(R.layout.item_grid, null);
//          给控件设置内容。 图片、名称、价格
            viewHolder.iv_thumb = view.findViewById(R.id.iv_thumb);
            viewHolder.tv_name = view.findViewById(R.id.tv_name);
            viewHolder.tv_price = view.findViewById(R.id.tv_price);
            viewHolder.btn_add = view.findViewById(R.id.btn_add);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        viewHolder.iv_thumb.setImageURI(Uri.parse(goodsInfo.picPath));
        viewHolder.tv_name.setText(goodsInfo.name);
        viewHolder.tv_price.setText(String.valueOf((int) goodsInfo.price));
        //增加按钮的点击事件
        //点击时，将商品名称和id传入
        viewHolder.btn_add.setOnClickListener(view1 -> {
//                addToCart(goodsInfo.id,goodsInfo.name);
            // 方式一，直接引用 ShoppingChannelActivity 中的 函数
//            ShoppingChannelActivity activity = (ShoppingChannelActivity) mContext;
//           ((ShoppingChannelActivity) mContext).addToCart(goodsInfo.id,goodsInfo.name);

           // 方式二、声明一个购物车的监听器接口和监听器接口对象，用接口的抽象方法传递函数。
            // 这种写法更加优雅，拓展性更强。
            mAddCartListener.addToCart(goodsInfo.id,goodsInfo.name);
        });

//          点击商品图片，跳转到商品页面
        viewHolder.iv_thumb.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ShoppingDetailActivity.class);
            intent.putExtra("goods_id", goodsInfo.id);
            mContext.startActivity(intent);
        });


        return view;
    }

    public final class ViewHolder {
        public ImageView iv_thumb;
        public TextView tv_name;
        public TextView tv_price;
        public Button btn_add;
    }



    // 定义声明一个加入购物车的监听器接口，接口一定要有个抽象方法
    public  interface AddCartListener {
        default void  addToCart(int goodsId, String goodsName) {

        }
    }

}
