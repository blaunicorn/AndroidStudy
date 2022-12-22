package com.example.chapter08.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter08.R;
import com.example.chapter08.entity.CartInfo;

import java.util.List;

public class CartAdapter extends BaseAdapter {

    //     新增：加入上下文和数据集合 参数
    private Context context;
    private List<CartInfo> cartInfoList;

    // 新增构造方法
    public CartAdapter(Context context, List<CartInfo> cartInfoList) {
        this.context = context;
        this.cartInfoList = cartInfoList;
    }


    @Override
    public int getCount() {
        return cartInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return cartInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {


        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder viewHolder;
        if (view == null) {
        viewHolder = new ViewHolder();

//            // 获取布局文件上下文、item_cart.xml的跟视图，变成java对象
             view = LayoutInflater.from(context).inflate(R.layout.item_cart, null);

//          给控件设置内容。 图片、名称、数量、价格、总价
            viewHolder.iv_thumb = view.findViewById(R.id.iv_thumb);


            viewHolder.tv_name = view.findViewById(R.id.tv_name);


            viewHolder.tv_desc = view.findViewById(R.id.tv_desc);


            viewHolder.tv_count = view.findViewById(R.id.tv_count);


            viewHolder.tv_price = view.findViewById(R.id.tv_price);

            viewHolder.tv_sum = view.findViewById(R.id.tv_sum);


            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        CartInfo info = cartInfoList.get(i);
        //          直接引用绝对路径方式
        viewHolder.iv_thumb.setImageURI(Uri.parse(info.goodsInfo.picPath));
        viewHolder.tv_name.setText(info.goodsInfo.name);
        viewHolder.tv_desc.setText(info.goodsInfo.description);
        viewHolder.tv_count.setText(String.valueOf((int) info.count));
        viewHolder.tv_price.setText(String.valueOf((int) info.goodsInfo.price));
        Log.d("wcy", "cartInfo: 2" + info);
        viewHolder.tv_sum.setText(String.valueOf((int) info.goodsInfo.price * info.count));
        return view;
    }

    public static class ViewHolder {
        public ImageView iv_thumb;
        public TextView tv_name;
        public TextView tv_desc;
        public TextView tv_count;
        public TextView tv_price;
        public TextView tv_sum;
    }
}
