package com.example.sec.woodongsa;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;



public class SubActivity1 extends FragmentActivity implements ActionBar.TabListener {
    private TabsPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private ActionBar actionBar;
    String selected_si,selected_gu;
    SharedPreferences si,gu;

    public int tabNum = 0;
    String[] bohum = {"실손","암", "태아/자녀", "종신", "연금", "저축", "화재",
            "자동차/운전자", "상해", "기타"};

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_activity1);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        // Tab Initialization

        Intent intent = getIntent();
        tabNum = intent.getIntExtra("tabNum",0);

        si = getApplicationContext().getSharedPreferences("si",0);
        gu = getApplicationContext().getSharedPreferences("gu",0);

        mViewPager.setAdapter(mAdapter);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String tab_name : bohum) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

/*
        actionBar.addTab(actionBar.newTab().setText(bohum[0]).setIcon(getResources().getDrawable(R.drawable.baby_))
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(bohum[1]).setIcon(getResources().getDrawable(R.drawable.t))
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(bohum[2]).setIcon(getResources().getDrawable(R.drawable.tt))
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(bohum[3]).setIcon(getResources().getDrawable(R.drawable.ttt))
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(bohum[4]).setIcon(getResources().getDrawable(R.drawable.tttt))
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(bohum[5]).setIcon(getResources().getDrawable(R.drawable.teeth))
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(bohum[6]).setIcon(getResources().getDrawable(R.drawable.tooth))
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(bohum[7]).setIcon(getResources().getDrawable(R.drawable.ttttt))
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(bohum[8])
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(bohum[9])
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(bohum[10])
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(bohum[11])
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(bohum[12])
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(bohum[13])
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(bohum[14])
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(bohum[15])
                .setTabListener(this));
*/

        actionBar.setSelectedNavigationItem(tabNum);


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
                actionBar.setTitle("");
           //     actionBar.setTitle("  " + si.getString("si","") + " > " + gu.getString("gu","") + " > " + bohum[position]);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }


    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        mViewPager.setCurrentItem(tab.getPosition());
        actionBar.setTitle("");
        //actionBar.setTitle("  " + bohum[tab.getPosition()]);
     //   actionBar.setTitle("  " + si.getString("si","") + " > " + gu.getString("gu","") + " > " + bohum[tab.getPosition()]);
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub_activity1, menu);
        return true;
    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("drawer_state",0);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("drawer_state",0);
            startActivity(intent);
            finish();
        }
        return false;
    }


}
