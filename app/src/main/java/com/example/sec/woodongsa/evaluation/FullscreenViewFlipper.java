package com.example.sec.woodongsa.evaluation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;
import android.widget.ZoomControls;

import com.example.sec.woodongsa.R;
import com.example.sec.woodongsa.Select_Service_Activity;
import com.imagezoom.ImageAttacher;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.viewpagerindicator.UnderlinePageIndicator;

public class FullscreenViewFlipper extends Activity {

//    ImageView imageView;
    OnSwipeTouchListener onSwipeTouchListener;
    int image_num = 0;
    ImageLoader imageLoader;
    //ZoomControls zoom;
    ImageButton imagebutton_left, imagebutton_right;
    String[] real_image;
    int ok_num = 0;
    int no_num = 0;
    int current_image_num = 0;
    ViewPager viewPager;
    PagerAdapter adapter;
    UnderlinePageIndicator mIndicator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);

        setContentView(R.layout.activity_fullscreen_view_flipper);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("");

        viewPager = (ViewPager) findViewById(R.id.pager);
        mIndicator = (UnderlinePageIndicator) findViewById(R.id.indicator);

/*
        imageView = (ImageView) findViewById(R.id.imageView);
      //  zoom = (ZoomControls) findViewById(R.id.zoomControls1);
        imagebutton_left = (ImageButton) findViewById(R.id.imagebutton_left);
        imagebutton_right = (ImageButton) findViewById(R.id.imagebutton_right);
*/
        /*
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_image_good)
                .showImageForEmptyUri(R.drawable.no_image)
                .showImageOnFail(R.drawable.no_image)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .writeDebugLogs()
                .defaultDisplayImageOptions(options)
                .diskCacheExtraOptions(4500,4000,null)
                .memoryCacheExtraOptions(4500,4000)
                .build();
        imageLoader.getInstance().init(config);
*/

        Intent intent = getIntent();
        final String[] image_url = intent.getStringArrayExtra("imageurl");
        ok_num = 0;
        no_num = 0;
        current_image_num = 0;
        //imageView.setImage(ImageSource.uri(image_url[current_image_num]));

        //imageView.setOnTouchListener(new Touch());

        for(int i = 0; i < image_url.length; i++){

            if(!image_url[i].equals("")){
                //imageLoader.getInstance().displayImage(image_url[i],imageView);
                //usingSimpleImage(imageView);
              //  viewFlipper.addView(image);
             //   viewFlipper.getCurrentView().setOnTouchListener(new Touch());
            } else {
                no_num++;
                if(no_num == 1){
                    ok_num = i;
                }
            }

        }

        real_image = new String[ok_num];
        for(int i = 0; i < ok_num; i++){
            real_image[i] = image_url[i];
        }

        adapter = new ViewPagerAdapter_fullscreen(FullscreenViewFlipper.this, real_image);
        viewPager.setAdapter(adapter);
        mIndicator.setFades(false);
        mIndicator.setViewPager(viewPager);


        //imageLoader.getInstance().displayImage(image_url[current_image_num],imageView);
        //usingSimpleImage(imageView);
/*
        imagebutton_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_image_num--;
                if(current_image_num<0){
                    current_image_num = ok_num - 1;
                    //imageView.setImage(ImageSource.uri(image_url[current_image_num]));
                    imageLoader.getInstance().displayImage(image_url[current_image_num],imageView);
                    usingSimpleImage(imageView);
                    imageLoader.getInstance().displayImage(image_url[current_image_num],imageView);
                    usingSimpleImage(imageView);
                    //imageView.setOnTouchListener(new Touch());
                } else {
                    //imageView.setImage(ImageSource.uri(image_url[current_image_num]));
                    imageLoader.getInstance().displayImage(image_url[current_image_num],imageView);
                    usingSimpleImage(imageView);
                    imageLoader.getInstance().displayImage(image_url[current_image_num],imageView);
                    usingSimpleImage(imageView);
                    //imageView.setOnTouchListener(new Touch());
                }

            }
        });

        imagebutton_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_image_num++;
                if(current_image_num >= ok_num){
                    current_image_num = 0;
                    //imageView.setImage(ImageSource.uri(image_url[current_image_num]));
                    imageLoader.getInstance().displayImage(image_url[current_image_num],imageView);
                    usingSimpleImage(imageView);
                    imageLoader.getInstance().displayImage(image_url[current_image_num],imageView);
                    usingSimpleImage(imageView);
                    //imageView.setOnTouchListener(new Touch());
                } else {
                    //imageView.setImage(ImageSource.uri(image_url[current_image_num]));
                    imageLoader.getInstance().displayImage(image_url[current_image_num],imageView);
                    usingSimpleImage(imageView);
                    imageLoader.getInstance().displayImage(image_url[current_image_num],imageView);
                    usingSimpleImage(imageView);
                    //imageView.setOnTouchListener(new Touch());
                }

            }
        });
*/
    }
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

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fullscreen_view_flipper, menu);
        return true;
    }
*/
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    switch (item.getItemId()) {
        case android.R.id.home:
            Intent intent = new Intent(FullscreenViewFlipper.this, Select_Service_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }
}
}
