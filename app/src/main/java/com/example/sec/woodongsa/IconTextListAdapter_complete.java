package com.example.sec.woodongsa;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


/**
 * Created by sec on 2015-06-26.
 */
public class IconTextListAdapter_complete extends BaseAdapter {

    private Context mContext;

    private List<IconTextItem_user_complete> mItems = new ArrayList<IconTextItem_user_complete>();

    public IconTextListAdapter_complete(Context context) {
        mContext = context;
    }

    public void addItem(IconTextItem_user_complete it) {
        mItems.add(it);
    }

    public void setListItems(List<IconTextItem_user_complete> lit) {
        mItems = lit;
    }

    public int getCount() {
        return mItems.size();
    }

    public Object getItem(int position) {
        return mItems.get(position);
    }

    public boolean areAllItemsSelectable() {
        return false;
    }

    public boolean isSelectable(int position) {
        try {
            return mItems.get(position).isSelectable();
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        IconTextView_user_complete itemView;
        if (convertView == null) {
            itemView = new IconTextView_user_complete(mContext, mItems.get(position));
        } else {
            itemView = (IconTextView_user_complete) convertView;

            itemView.setText(0, mItems.get(position).getData(0));
            itemView.setText(1, mItems.get(position).getData(1));
            itemView.setText(2, mItems.get(position).getData(2));
            itemView.setText(3, mItems.get(position).getData(3));
            itemView.setText(4, mItems.get(position).getData(4));
            itemView.setText(5, mItems.get(position).getData(5));
            itemView.setText(6, mItems.get(position).getData(6));
        }

        return itemView;
    }

}
