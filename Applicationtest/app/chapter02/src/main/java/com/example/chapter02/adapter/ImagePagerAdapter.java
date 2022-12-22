package com.example.chapter02.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.example.chapter02.utils.utils;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {

    private final Context mContext;
    private final ArrayList<String> mList;
    // 声明一个图像视图列表
    private List<ImageView> mViewList = new ArrayList<>();

    public ImagePagerAdapter(Context mContext, ArrayList<String> mList) {
        this.mContext = mContext;
        this.mList = mList;

        // 给每个商品分配一个专用的图像视图，并增加到集合中取
        for (String s : mList) {
            ImageView view = new ImageView(mContext);
//            view.setImageResource(utils.drawable(mContext, s));
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mViewList.add(view);
        }
    }

    @Override
    public int getCount() {
        return 0 | mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return  view == object;

//        return false;
    }

    @NonNull
    @Override
//   实例化指定位置的页面元素item，并将其添加到容器中
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView item = mViewList.get(position);
        container.addView(item);
        return item;
//        return super.instantiateItem(container, position);
    }

    @Override
//    销毁
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView(mViewList.get(position));

        super.destroyItem(container, position, object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position);
//        return super.getPageTitle(position);
    }
}
