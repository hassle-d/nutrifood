package com.nutrifood2.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nutrifood2.Models.BasicItem;
import com.nutrifood2.R;
import com.nutrifood2.Utils.CallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListRecyclerViewAdapter
        extends RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder> {

    private final List<BasicItem> mValues;
    private final Map<Integer, Integer> mViewTypeMap = new HashMap<>();
    private int mViewType = R.integer.BASIC_VIEW;
    private CallBack mCallBack = null;
    private Context mContext;

    public ListRecyclerViewAdapter(Context context, List<BasicItem> items,
                                   int viewType, CallBack action) {
        mValues = items;
        mCallBack = action;
        mContext = context;
        mViewType = viewType;

        initialize();
    }

    public ListRecyclerViewAdapter(Context context, List<BasicItem> items, CallBack action) {
        mValues = items;
        mCallBack = action;
        mContext = context;

        initialize();
    }

    public ListRecyclerViewAdapter(Context context, List<BasicItem> items, int viewType) {
        mValues = items;
        mViewType = viewType;
        mContext = context;

        initialize();
    }

    public ListRecyclerViewAdapter(List<BasicItem> items, int viewType, CallBack callBack) {
        mValues = items;
        mViewType = viewType;
        mCallBack = callBack;

        initialize();
    }

    public ListRecyclerViewAdapter(List<BasicItem> items, int viewType) {
        mValues = items;
        mViewType = viewType;

        initialize();
    }

    public ListRecyclerViewAdapter(int viewType) {
        mValues = new ArrayList<>();
        mViewType = viewType;

        initialize();
    }

    public void clearList() { mValues.clear(); }

    public void addItem(BasicItem item)
    {
        mValues.add(item);
    }

    private void initialize()
    {
        mViewTypeMap.put(R.integer.CARD_VIEW, R.layout.card_item);
        mViewTypeMap.put(R.integer.CARD_IMG_VIEW, R.layout.card_img_item);
        mViewTypeMap.put(R.integer.BASIC_VIEW, R.layout.basic_item);
        mViewTypeMap.put(R.integer.LIST_ITEM_VIEW, R.layout.list_item);
        mViewTypeMap.put(R.integer.COMMENT_VIEW, R.layout.comment_item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        if (mViewTypeMap.containsKey(mViewType))
            view = LayoutInflater.from(parent.getContext())
                    .inflate(mViewTypeMap.get(mViewType), parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        String name = holder.mItem.Name();
        String content = holder.mItem.Content();
        String date = holder.mItem.Date();
        int img_id = holder.mItem.ImageId();

        if (name != null && holder.mIdView != null)
            holder.mIdView.setText(name);
        if (content != null && holder.mContentView != null)
            holder.mContentView.setText(content);
        if (date != null && holder.mDateView != null)
            holder.mDateView.setText(date);
        if (holder.mPosView != null)
            holder.mPosView.setText(String.valueOf(position+1));
        if (holder.mImageView != null) {
            if (img_id != -1)
                holder.mImageView.setImageResource(img_id);
        }

        if (mCallBack != null)
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallBack.onItemClicked(holder.mItem);
                }
            });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mDateView;
        public final ImageView mImageView;
        public final TextView mPosView;
        public BasicItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_title);

            if (mViewType == R.integer.CARD_VIEW || mViewType == R.integer.BASIC_VIEW) {
                mContentView = (TextView) view.findViewById(R.id.item_content);
                mImageView = null;
                mPosView = null;
                mDateView = null;
            }
            else if (mViewType == R.integer.LIST_ITEM_VIEW) {
                mPosView = (TextView) view.findViewById(R.id.item_position);
                mContentView = null;
                mImageView = null;
                mDateView = null;
            }
            else if (mViewType == R.integer.COMMENT_VIEW) {
                mDateView = (TextView) view.findViewById(R.id.item_date);
                mContentView = (TextView) view.findViewById(R.id.item_content);
                mPosView = null;
                mImageView = null;
            }
            else {
                mImageView = (ImageView) view.findViewById(R.id.item_img);
                mContentView = null;
                mPosView = null;
                mDateView = null;
            }
        }
    }
}

