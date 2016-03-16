package com.example.sec.woodongsa;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by sec on 2015-05-24.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Integer[] mThumbIds = {
            /*
            R.drawable.main_grid1, R.drawable.main_grid2,
            R.drawable.main_grid3, R.drawable.main_grid4,
            R.drawable.main_grid5, R.drawable.main_grid6,
            R.drawable.main_grid7, R.drawable.main_grid8,
            R.drawable.main_grid9, R.drawable.main_grid10,
            R.drawable.main_grid11, R.drawable.main_grid12,
            R.drawable.main_grid13, R.drawable.main_grid14,
            R.drawable.main_grid15, R.drawable.main_grid16
            */
    };

    public ImageAdapter(Context c){
        mContext = c;
    }
    public int getCount(){
        return mThumbIds.length;
    }
    public Object getItem(int position){
        return null;
    }

    public long getItemId(int position){
        return 0;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(600,500));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5,5,5,5);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

}
