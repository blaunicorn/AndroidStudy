package com.example.chapter02.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.chapter02.entity.BillInfo;

import java.util.List;

public class BillListAdapter extends BaseAdapter {


    private final Context mContext;
    private final List<BillInfo> mBillInfoList;

    public BillListAdapter(Context context, List<BillInfo> billInfoList) {
        this.mContext = context;
        this.mBillInfoList = billInfoList;
    }

    @Override
    public int getCount() {
        return mBillInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return mBillInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mBillInfoList.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
