package com.example.sec.woodongsa.evaluation.Comment;

/**
 * Created by sec on 2015-07-21.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class IconTextListAdapter_comment extends BaseAdapter {

    private Context mContext;

    private List<IconTextItem_Comment> mItems = new ArrayList<IconTextItem_Comment>();

    public IconTextListAdapter_comment(Context context) {
        mContext = context;
    }

    public void addItem(IconTextItem_Comment it) {
        mItems.add(it);
    }

    public void setListItems(List<IconTextItem_Comment> lit) {
        mItems = lit;
    }

    public int getCount() {
        return mItems.size();
    }

    public Object getItem(int position) {
        return mItems.get(position-1);
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
        IconTextView_comment itemView;
        if (convertView == null) {
            itemView = new IconTextView_comment(mContext, mItems.get(position));
        } else {
            itemView = (IconTextView_comment) convertView;

            itemView.setIcon(mItems.get(position).getIcon());
            itemView.setText(0, mItems.get(position).getData(0));
            itemView.setText(1, mItems.get(position).getData(1));
            itemView.setText(2, mItems.get(position).getData(2));
            itemView.setText(3, mItems.get(position).getData(3));
            itemView.setText(4, mItems.get(position).getData(4));
            itemView.setText(5, mItems.get(position).getData(5));
        }

        return itemView;
    }

}
