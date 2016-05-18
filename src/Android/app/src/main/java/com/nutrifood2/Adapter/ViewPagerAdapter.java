package com.nutrifood2.Adapter;

// Import needed to execute the code
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the custom lists
 *
 * @author DimitriAndMathias
 * @version 2016.0501
 * @since 2.0
 */
public class ViewPagerAdapter extends FragmentPagerAdapter
{
    // Private variables
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    /**
     * Public constructor
     *
     * @author DimitriAndMathias
     * @param manager The given fragment's manager
     * @version 2016.0501
     */
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param position The given item's position
     * @version 2010.1105
     * @since 1.0
     * @return The asked fragment
     */
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The list's count
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param fragment The given fragment
     * @param title The given fragment's title
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param position The given fragment's position
     * @version 2010.1105
     * @since 1.0
     * @return The char sequence
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
