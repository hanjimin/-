package com.example.sec.woodongsa;

/**
 * Created by sec on 2015-06-02.
 */
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class IconTextView_mypage_user extends LinearLayout {

    private ImageView mIcon;
    private TextView mText01;
    private Context context;
    private ImageLoader imageLoader;


    public IconTextView_mypage_user(Context context, IconTextItem_mypage_user aItem) {
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
                .build();



        imageLoader.getInstance().init(config);


        // Layout Inflation
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mypage_user_list_item, this, true);

        // Set Icon
        mIcon = (ImageView) findViewById(R.id.sub1_list_item_image);
        imageLoader.getInstance().displayImage(aItem.getIcon(), mIcon, options);

        mText01 = (TextView) findViewById(R.id.list_time);
        mText01.setText(aItem.getData(1));


    }

    public void setText(int index, String data) {
        if(index == 0){

        }else if (index == 1) {
            mText01.setText(data);
        } else {
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