package com.example.chapter08.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter08.R;
import com.example.chapter08.entity.Planet;

import java.util.List;

public class PlanetBaseAdapter extends BaseAdapter {
//    定义适配属性:上下文和 数组集合
    private Context mContext;
    private List<Planet> mPlanetList;

//    生成一个构造方法
    public PlanetBaseAdapter(Context mContext, List<Planet> mPlanetList) {
        this.mContext = mContext;
        this.mPlanetList = mPlanetList;
    }

    @Override
    public int getCount() {
//        return 0;
        return  mPlanetList.size(); // 返回总共有多少个元素
    }

    @Override
    public Object getItem(int i) {
//        return null;
        return  mPlanetList.get(i);
    }

    @Override
    public long getItemId(int i) {
//        return 0;
        return  i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parentViewGroup) {
//        return null;
        // 先判断视图是否存在，不存在再创建
        ViewHolder holder;
        if (convertView== null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list, null);
           holder = new ViewHolder();
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            // 把视图持有者保存到转换视图中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Planet planet = mPlanetList.get(position);
        holder.iv_icon.setImageResource(planet.image);
        holder.tv_name.setText(planet.name);
        holder.tv_desc.setText(planet.desc);




        // 依据布局文件item_list.xml生成转换视图对象
//       通过布局加载器LayoutInflater 返回列对象给视图
//        这种是每次都新建，浪费资源，实际上可以先判断视图是否存在，不存在再创建
//        Vew view = LayoutInflater.from(mContext).inflate(R.layout.item_list, null);
//        // 设置数据
//        ImageView iv_icon = view.findViewById(R.id.iv_icon);
//        TextView tv_name = view.findViewById(R.id.tv_name);
//        TextView tv_desc = view.findViewById(R.id.tv_desc);
//        Planet planet = mPlanetList.get(position);
//        iv_icon.setImageResource(planet.image);
//        tv_name.setText(planet.name);
//        tv_desc.setText(planet.desc);
        return  convertView;
    }

//    把视图里的元素也抽离出来
    public  final class ViewHolder {
        public ImageView iv_icon;
        public  TextView tv_name;
        public  TextView tv_desc;
}
}
