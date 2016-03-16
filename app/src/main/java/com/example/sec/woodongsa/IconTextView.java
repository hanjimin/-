package com.example.sec.woodongsa;

/**
 * Created by sec on 2015-06-02.
 */
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class IconTextView extends LinearLayout {

    private ImageView mIcon;
    private TextView sub1_list_item_id;
    private TextView sub1_list_item_sex;
    private TextView sub1_list_item_address;
    private TextView sub1_list_item_date;
    private TextView sub1_list_item_corp_logo;
    private TextView sub1_list_item_corp_name;
    private TextView sub1_list_item_code;
    private TextView sub1_list_item_introduce;

    private Context context;
    private ImageLoader imageLoader;

    public IconTextView(Context context, IconTextItem aItem) {
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
        inflater.inflate(R.layout.sub1_list_item, this, true);

        // Set Icon
        mIcon = (ImageView) findViewById(R.id.sub1_list_item_image);
        imageLoader.getInstance().displayImage(aItem.getIcon(), mIcon, options);


    }

    public void setText(int index, String data) {

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
        imageLoader.getInstance().displayImage(icon, mIcon);
    }

}