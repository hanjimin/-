package com.example.sec.woodongsa;

/**
 * Created by sec on 2015-05-28.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new Tab1();
            case 1:
                // Games fragment activity
                return new Tab2();
            case 2:
                // Movies fragment activity
                return new Tab3();
            case 3:
                return new Tab4();
            case 4:
                return new Tab5();
            case 5:
                return new Tab6();
            case 6:
                return new Tab7();
            case 7:
                return new Tab8();
            case 8:
                return new Tab9();
            case 9:
                return new Tab10();
        }
        return null;
    }
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 10;
    }
}
