package com.example.chapter02.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chapter02.fragment.BillFragment;

public class BillPagerAdapter extends FragmentPagerAdapter {
    private final int mYear;

    public BillPagerAdapter(@NonNull FragmentManager fm, int year) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mYear = year;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        return null;
        int month = position +1;
        String zeroMonth = month<10?"0"+month : String.valueOf(month);
        // 2022-12   2022-8
        String yearMonth = mYear + "-" + zeroMonth;
        Log.d("wcy", "getItem: "+ yearMonth);
        return BillFragment.newInstance(yearMonth);
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        return  (position+1) + "月份";
    }
}
