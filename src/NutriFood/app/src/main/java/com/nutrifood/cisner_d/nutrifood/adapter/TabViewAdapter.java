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
                    return new Profil();
                }
            }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
