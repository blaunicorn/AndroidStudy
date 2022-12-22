package com.example.chapter02.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chapter02.fragment.BillFragment;

public class BillPagerAdpater extends FragmentPagerAdapter {
    public BillPagerAdpater(@NonNull FragmentManager fm) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        return null;
        return BillFragment.newInstance();
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
