package com.example.android.touristapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int  mNoOfTabs;

    public ViewPagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                Food_Fragment food = new Food_Fragment();
                return food;
            case 1:
                Events_Fragment  events = new Events_Fragment();
                return events;
            case 2:
                Hotels_Fragment hotels = new Hotels_Fragment();
                return hotels;

            case 3:
                Sights_Fragment sights= new Sights_Fragment();
                return sights;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
