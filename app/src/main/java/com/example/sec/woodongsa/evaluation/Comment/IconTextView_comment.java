package com.example.sec.woodongsa.evaluation.Comment;

/**
 * Created by sec on 2015-07-21.
 */
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sec.woodongsa.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class IconTextView_comment extends LinearLayout {

    private ImageView mIcon;
    private TextView mText01, mText02, mText03, mText04, mText05, mText06;
    private Context context;
    private ImageLoader imageLoader;


    public IconTextView_comment(Context context, IconTextItem_Comment aItem) {
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
        inflater.inflate(R.layout.comment_list_item, this, true);

        // Set Icon
        mIcon = (ImageView) findViewById(R.id.comment_imageview);
        imageLoader.getInstance().displayImage(aItem.getIcon(), mIcon, options);

        mText01 = (TextView) findViewById(R.id.comment_co_name);
        mText01.setText(aItem.getData(0));

        mText02 = (TextView) findViewById(R.id.comment_co_si);
        mText02.setText(aItem.getData(1));

        mText03 = (TextView) findViewById(R.id.comment_co_gu);
        mText03.setText(aItem.getData(2));

        mText04 = (TextView) findViewById(R.id.comment_date);
        mText04.setText(aItem.getData(3));

        mText05 = (TextView) findViewById(R.id.comment_content);
        mText05.setText(aItem.getData(4));
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