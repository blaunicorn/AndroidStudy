package com.example.chapter02.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chapter02.R;
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_bill, null);
            holder.tv_date = view.findViewById(R.id.tv_date);
            holder.tv_remark = view.findViewById(R.id.tv_remark);
            holder.tv_amount = view.findViewById(R.id.tv_amount);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // 给视图赋值
        BillInfo billInfo = mBillInfoList.get(position);
        holder.tv_date.setText(billInfo.date);
        holder.tv_remark.setText(billInfo.remark);
//        收入为正数 ，支出为负数
        holder.tv_amount.setText(String.format("%s%d元", billInfo.type == 0 ? "+" : "-", (int) billInfo.amount));
        return view;
    }

    public final class ViewHolder {
        public TextView tv_date;
        public TextView tv_remark;
        public TextView tv_amount;
    }
}
