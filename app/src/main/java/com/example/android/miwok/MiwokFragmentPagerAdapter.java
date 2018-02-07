package com.example.android.miwok;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Bhati on 07-Feb-18.
 */

public class MiwokFragmentPagerAdapter extends FragmentPagerAdapter {

    String[] titles;

    public MiwokFragmentPagerAdapter(FragmentManager fm,String[] titles) {
        super(fm);
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
       switch (position)
       {
           case 0:return new NumberFragment();
           case 1:return new FamilyFragment();
           case 2:return new ColorsFragment();
           default:return new PhrasesFragment();
       }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
       return titles[position];
    }
}
