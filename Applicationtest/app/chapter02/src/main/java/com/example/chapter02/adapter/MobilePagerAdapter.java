package com.example.chapter02.adapter;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chapter02.FragmentDynamicActivity;
import com.example.chapter02.R;
import com.example.chapter02.fragment.DynamicFragment;
import com.example.chapter02.utils.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MobilePagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<String> mList;
    private int resId;
    public MobilePagerAdapter(@NonNull FragmentManager fm, ArrayList<String> mList) {
        super(fm);
        this.mList = mList;
    }

    public MobilePagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<String> mList) {
        super(fm, behavior);
        this.mList = mList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        String info = mList.get(position);
//        根据变量动态获取drawable图片ID
        resId = 0;
        try {
            Field idField = R.drawable.class.getField(info);

            resId = idField.getInt(idField);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        Log.d("wcy", "getItem: "+info);
        return DynamicFragment.newInstance(position, R.drawable.c01,info);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        return mList.get(position);
//        return super.getPageTitle(position);
    }
}
