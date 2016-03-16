package com.example.sec.woodongsa;

/**
 * Created by sec on 2015-05-28.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter_consultant extends FragmentPagerAdapter {

    public TabsPagerAdapter_consultant(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new Mypage_consultant_tab0();
            case 1:
                // Games fragment activity
                return new Mypage_consultant_tab1();
            case 2:
                // Movies fragment activity
                return new Mypage_consultant_tab2();
            case 3:
                return new Mypage_consultant_tab3();
            case 4:
                return new Mypage_consultant_tab4();
            case 5:
                return new Mypage_consultant_tab5();

        }
        return null;
    }
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 6;
    }
}
