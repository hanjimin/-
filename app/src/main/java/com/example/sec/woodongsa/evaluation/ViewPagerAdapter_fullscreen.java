package com.example.sec.woodongsa.evaluation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.sec.woodongsa.R;
import com.example.sec.woodongsa.TouchImageView;
import com.imagezoom.ImageAttacher;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.squareup.picasso.Picasso;

/**
 * Created by sec on 2015-07-31.
 */
public class ViewPagerAdapter_fullscreen extends PagerAdapter {
    // Declare Variables
    Context context;
    String[] images;
    ImageLoader imageLoader;
    LayoutInflater inflater;

    int rotate_state = 0;

    public ViewPagerAdapter_fullscreen(Context context, String images[]) {
        this.context = context;
        this.images = images;

    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables

        final TouchImageView imgflag;
        ImageButton btn_rotate_image;


        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.fullscreen_viewpager_item, container,
                false);

        // Locate the TextViews in viewpager_item.xml
/*
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_image_good)
                .showImageForEmptyUri(R.drawable.no_image)
                .showImageOnFail(R.drawable.no_image)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .considerExifParams(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .writeDebugLogs()
                .defaultDisplayImageOptions(options)
                .memoryCacheExtraOptions(4500, 4000)
                .build();
        imageLoader.getInstance().init(config);
*/
        imgflag = (TouchImageView) itemView.findViewById(R.id.full_image);
        btn_rotate_image = (ImageButton) itemView.findViewById(R.id.btn_rotate_image);
        // Capture position and set to the ImageView
        //imageLoader.getInstance().displayImage(images[position],imgflag);

        Picasso.with(context)
                .load(images[position])
                .placeholder(R.drawable.loading_image_good)
                .error(R.drawable.no_image)
                .into(imgflag);

        final int position_temp = position;

        rotate_state = 0;

        btn_rotate_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rotate_state == 0){
                    Picasso.with(context)
                            .load(images[position_temp])
                            .placeholder(R.drawable.loading_image_good)
                            .error(R.drawable.no_image)
                            .rotate(90)
                            .into(imgflag);
                } else if(rotate_state == 1){
                    Picasso.with(context)
                            .load(images[position_temp])
                            .placeholder(R.drawable.loading_image_good)
                            .error(R.drawable.no_image)
                            .rotate(180)
                            .into(imgflag);
                } else if(rotate_state == 2){
                    Picasso.with(context)
                            .load(images[position_temp])
                            .placeholder(R.drawable.loading_image_good)
                            .error(R.drawable.no_image)
                            .rotate(270)
                            .into(imgflag);
                } else{
                    Picasso.with(context)
                            .load(images[position_temp])
                            .placeholder(R.drawable.loading_image_good)
                            .error(R.drawable.no_image)
                            .into(imgflag);
                }

                rotate_state++;

                if(rotate_state > 3){
                    rotate_state = 0;
                }

            }
        });

        //UrlImageViewHelper.setUrlDrawable(imgflag,images[position]);
        //usingSimpleImage(imgflag);
        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
