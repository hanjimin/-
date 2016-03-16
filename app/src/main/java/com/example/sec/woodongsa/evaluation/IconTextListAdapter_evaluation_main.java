package com.example.sec.woodongsa.evaluation;

/**
 * Created by sec on 2015-06-02.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class IconTextListAdapter_evaluation_main extends BaseAdapter {

    private Context mContext;

    private List<IconTextItem_evaluation_main> mItems = new ArrayList<IconTextItem_evaluation_main>();

    public IconTextListAdapter_evaluation_main(Context context) {
        mContext = context;
    }

    public void addItem(IconTextItem_evaluation_main it) {
        mItems.add(it);
    }

    public void setListItems(List<IconTextItem_evaluation_main> lit) {
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
        IconTextView_evaluation_main itemView;
        if (convertView == null) {
            itemView = new IconTextView_evaluation_main(mContext, mItems.get(position));
        } else {
            itemView = (IconTextView_evaluation_main) convertView;

            itemView.setIcon(mItems.get(position).getIcon());
            itemView.setText(0, mItems.get(position).getData(0));
            itemView.setText(1, mItems.get(position).getData(1));
            itemView.setText(2, mItems.get(position).getData(2));
            itemView.setText(3, mItems.get(position).getData(3));
            itemView.setText(4, mItems.get(position).getData(4));
            itemView.setText(5, mItems.get(position).getData(5));
            itemView.setText(6, mItems.get(position).getData(6));
            itemView.setText(7, mItems.get(position).getData(7));
            itemView.setText(8, mItems.get(position).getData(8));
        }

        return itemView;
    }

}