package com.nutrifood.cisner_d.nutrifood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nutrifood.cisner_d.nutrifood.Main.ListItem;
import com.nutrifood.cisner_d.nutrifood.R;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<ListItem> mNavItems;
    int[]               mLayouts = { R.layout.list_item };

    public ListAdapter(Context context, ArrayList<ListItem> navItems) {
        mContext = context;
        mNavItems = navItems;
    }

    @Override
    public int getCount() {
        return mNavItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mNavItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setList(ArrayList<ListItem> navItems)
    {
        mNavItems = navItems;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mLayouts[mNavItems.get(position)._type], null);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);

        titleView.setText( mNavItems.get(position)._title );
        subtitleView.setText(mNavItems.get(position)._subtitle);

        return view;
    }
}
