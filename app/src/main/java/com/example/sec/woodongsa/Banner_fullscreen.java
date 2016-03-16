package com.example.sec.woodongsa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.RectF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.imagezoom.ImageAttacher;
import com.viewpagerindicator.UnderlinePageIndicator;


public class Banner_fullscreen extends Activity {


    int[] banner_resource = {R.drawable.banner_test1, R.drawable.banner_test2,R.drawable.banner_test3};
    ViewPager viewPager;
    PagerAdapter adapter;
    UnderlinePageIndicator mIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
        layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount= 0.5f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_banner_fullscreen);

//        getActionBar().setTitle("");


        viewPager = (ViewPager) findViewById(R.id.pager);
        mIndicator = (UnderlinePageIndicator) findViewById(R.id.indicator);



        Intent intent = getIntent();
        int selected_image = intent.getIntExtra("banner_num",0);



        adapter = new ViewPagerAdapter_Banner(Banner_fullscreen.this, banner_resource);
        viewPager.setAdapter(adapter);
        mIndicator.setFades(false);
        mIndicator.setViewPager(viewPager);

        //usingSimpleImage(imageView);
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_banner_fullscreen, menu);
        return true;
    }
*/
public void usingSimpleImage(ImageView imageView) {
    ImageAttacher mAttacher = new ImageAttacher(imageView);
    ImageAttacher.MAX_ZOOM = 2.0f; // Double the current Size
    ImageAttacher.MIN_ZOOM = 0.5f; // Half the current Size
    MatrixChangeListener mMaListener = new MatrixChangeListener();
    mAttacher.setOnMatrixChangeListener(mMaListener);
    PhotoTapListener mPhotoTap = new PhotoTapListener();
    mAttacher.setOnPhotoTapListener(mPhotoTap);
}

    private class PhotoTapListener implements ImageAttacher.OnPhotoTapListener {

        @Override
        public void onPhotoTap(View view, float x, float y) {
        }
    }

    private class MatrixChangeListener implements ImageAttacher.OnMatrixChangedListener {

        @Override
        public void onMatrixChanged(RectF rect) {

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
