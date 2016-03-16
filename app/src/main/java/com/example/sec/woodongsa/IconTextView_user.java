package com.example.sec.woodongsa;

/**
 * Created by sec on 2015-06-26.
 */
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ?꾩씠?쒖쑝濡?蹂댁뿬以?酉??뺤쓽
 *
 * @author Mike
 *
 */
public class IconTextView_user extends LinearLayout {

    private TextView mText01;

    /**
     * TextView 02
     */
    private TextView mText02;

    /**
     * TextView 03
     */
    private TextView mText03,mText04,mText05,mText06,mText07;

    public IconTextView_user(Context context, IconTextItem_user aItem) {
        super(context);

        // Layout Inflation
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.user_list_item, this, true);


        // Set Text 01
        mText01 = (TextView) findViewById(R.id.user_name);
        mText01.setText(aItem.getData(0));

        // Set Text 02


        // Set Text 03
        mText03 = (TextView) findViewById(R.id.user_si);
        mText03.setText(aItem.getData(2));

        mText04 = (TextView) findViewById(R.id.user_gu);
        mText04.setText(aItem.getData(3));

        mText05 = (TextView) findViewById(R.id.user_phone_num);
        mText05.setText(aItem.getData(4));

        mText06 = (TextView) findViewById(R.id.user_email);
        mText06.setText(aItem.getData(5));

        mText07 = (TextView) findViewById(R.id.user_date);
        mText07.setText(aItem.getData(6));

    }

    /**
     * set Text
     *
     * @param index
     * @param data
     */
    public void setText(int index, String data) {
        if (index == 0) {
            mText01.setText(data);
        } else if (index == 1) {

        } else if (index == 2) {
            mText03.setText(data);
        } else if (index == 3) {
            mText04.setText(data);
        } else if (index == 4) {
            mText05.setText(data);
        } else if (index == 5) {
            mText06.setText(data);
        } else if (index == 6) {
            mText07.setText(data);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * set Icon
     *
     * @param icon
     */


}

