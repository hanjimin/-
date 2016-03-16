package com.example.sec.woodongsa.evaluation;

/**
 * Created by sec on 2015-06-02.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sec.woodongsa.Evaluation_main;
import com.example.sec.woodongsa.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.picasso.Picasso;


public class IconTextView_evaluation_main extends LinearLayout {

    private ImageView mIcon;
    private TextView mText01, mText02, mText03, mText04, mText05, mText06, mText07, mText08, mText09;
    private Context context;
    ImageLoader imageLoader;
    DisplayImageOptions options;


    public IconTextView_evaluation_main(Context context, IconTextItem_evaluation_main aItem) {
        super(context);

        this.context = context;

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_image_good)
                .showImageForEmptyUri(R.drawable.no_image)
                .showImageOnFail(R.drawable.no_image)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .writeDebugLogs()
                .defaultDisplayImageOptions(options)
                .diskCacheExtraOptions(480,320,null)
                .memoryCacheExtraOptions(480,320)
                .build();



        imageLoader.getInstance().init(config);


        //imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        // Layout Inflation
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.evaluation_main_list_item, this, true);

        // Set Icon
        mIcon = (ImageView) findViewById(R.id.evaluation_main_imageview);
        System.out.println(aItem.getIcon() + "******************");

        imageLoader.getInstance().displayImage(aItem.getIcon(), mIcon, options);


        mText01 = (TextView) findViewById(R.id.evaluation_main_item_title);
        mText01.setText(aItem.getData(0));

        mText02 = (TextView) findViewById(R.id.evaluation_main_item_nickname);
        mText02.setText(aItem.getData(1));

        mText03 = (TextView) findViewById(R.id.evaluation_main_text_si);
        mText03.setText(aItem.getData(2));

        mText04 = (TextView) findViewById(R.id.evaluation_main_text_gu);
        mText04.setText(aItem.getData(3));

        mText05 = (TextView) findViewById(R.id.evaluation_main_item_date);
        mText05.setText(aItem.getData(4));

        mText06 = (TextView) findViewById(R.id.evaluation_main_item_content);
        mText06.setText(aItem.getData(5));

        mText07 = (TextView) findViewById(R.id.evaluation_main_item_hit);
        mText07.setText(aItem.getData(6));

        mText08 = (TextView) findViewById(R.id.evaluation_main_item_comment);
        mText08.setText(aItem.getData(7));


    }

    public void setText(int index, String data) {
        if(index == 0){
            mText01.setText(data);
        }else if (index == 1) {
            mText02.setText(data);
        }else if (index == 2) {
            mText03.setText(data);
        }else if (index == 3) {
            mText04.setText(data);
        }else if (index == 4) {
            mText05.setText(data);
        }else if (index == 5) {
            mText06.setText(data);
        }else if (index == 6) {
            mText07.setText(data);
        }else if (index == 7) {
            mText08.setText(data);
        }else if (index == 8) {

        }else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * set Icon
     *
     * @param icon
     */
    public void setIcon(String icon) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_image_good)
                .showImageForEmptyUri(R.drawable.no_image)
                .showImageOnFail(R.drawable.no_image)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .writeDebugLogs()
                .defaultDisplayImageOptions(options)
                .diskCacheExtraOptions(480,320,null)
                .build();
        imageLoader.getInstance().init(config);
        imageLoader.getInstance().displayImage(icon,mIcon);
    }


}