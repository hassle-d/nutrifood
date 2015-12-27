package com.nutrifood.cisner_d.nutrifood.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nutrifood.cisner_d.nutrifood.*;

public class TabViewAdapter extends FragmentPagerAdapter {

    public TabViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
            switch (i) {
                case 0: {
                    return new Home();
                }
                case 1: {
                    return new Category();
                }
                case 2: {
                    return new Dishes();
                }
                case 3: {
                    return new Profil();
                }
                case 4: {
                    return new CreateDishes();
                }
                case 5: {
                    return new Dishe();
                }
            }
        return null;
    }

    @Override
    public int getCount() {
        return 6;
    }
}
