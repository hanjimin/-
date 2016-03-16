package com.example.sec.woodongsa;

/**
 * Created by sec on 2015-06-02.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class IconTextListAdapter_mypage_user extends BaseAdapter {

    private Context mContext;

    private List<IconTextItem_mypage_user> mItems = new ArrayList<IconTextItem_mypage_user>();

    public IconTextListAdapter_mypage_user(Context context) {
        mContext = context;
    }

    public void addItem(IconTextItem_mypage_user it) {
        mItems.add(it);
    }

    public void setListItems(List<IconTextItem_mypage_user> lit) {
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
        IconTextView_mypage_user itemView;
        if (convertView == null) {
            itemView = new IconTextView_mypage_user(mContext, mItems.get(position));
        } else {
            itemView = (IconTextView_mypage_user) convertView;

            itemView.setIcon(mItems.get(position).getIcon());
            itemView.setText(0, mItems.get(position).getData(0));
            itemView.setText(1, mItems.get(position).getData(1));
        }

        return itemView;
    }

}