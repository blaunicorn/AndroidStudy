package com.example.chapter02.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chapter02.fragment.LaunchFragment;
import com.example.chapter02.fragment.LaunchFromUrlFragment;

public class LaunchImproveFromUrlAdapter extends FragmentPagerAdapter {


    private final String[] mImageArray;

    public LaunchImproveFromUrlAdapter(@NonNull FragmentManager fm, String[] imageArray) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mImageArray = imageArray;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return LaunchFromUrlFragment.newInstance(getCount(),position, mImageArray[position]);
    }

    @Override
    public int getCount() {
        return mImageArray.length;
    }
}
