package com.example.sec.woodongsa;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter3 extends FragmentPagerAdapter {

    public TabsPagerAdapter3(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new Mypage_tab0();
            case 1:
                // Games fragment activity
                return new Mypage_tab1();
            case 2:
                // Movies fragment activity
                return new Mypage_tab2();
            case 3:
                // Movies fragment activity
                return new Mypage_tab3();
            case 4:
                // Movies fragment activity
                return new Mypage_tab4();

        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 5;
    }

}
