package com.nutrifood2.Adapter;

// Import needed to execute the code
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

/**
 * Adapter for the recycler view list
 *
 * @author DimitriAndMathias
 * @version 2016.0501
 * @since 2.0
 */
public class ListRecyclerViewAdapter
        extends RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder>
{

    // Private variables
    private final List<BasicItem> mValues;
    private final Map<Integer, Integer> mViewTypeMap = new HashMap<>();
    private int mViewType = R.integer.BASIC_VIEW;
    private CallBack mCallBack = null;
    private Context mContext;

    /**
     * Public constructor that saves the given context, items, view type and action
     *
     * @author DimitriAndMathias
     * @param context The given layout's context
     * @param items The given layout's items
     * @param viewType The given layout's view type
     * @param action The given layout's action
     * @version 2016.0501
     */
    public ListRecyclerViewAdapter(Context context, List<BasicItem> items,
                                   int viewType, CallBack action)
    {
        mValues = items;
        mCallBack = action;
        mContext = context;
        mViewType = viewType;

        initialize();
    }

    /**
     * Public constructor that saves the given context, items and action
     *
     * @author DimitriAndMathias
     * @param context The given layout's context
     * @param items The given layout's items
     * @param action The given layout's action
     * @version 2016.0501
     */
    public ListRecyclerViewAdapter(Context context, List<BasicItem> items, CallBack action)
    {
        mValues = items;
        mCallBack = action;
        mContext = context;

        initialize();
    }

    /**
     * Public constructor that saves the given context, items and view type
     *
     * @author DimitriAndMathias
     * @param context The given layout's context
     * @param items The given layout's items
     * @param viewType The given layout's view type
     * @version 2016.0501
     */
    public ListRecyclerViewAdapter(Context context, List<BasicItem> items, int viewType)
    {
        mValues = items;
        mViewType = viewType;
        mContext = context;

        initialize();
    }

    /**
     * Public constructor that saves the given items, view type and call back
     *
     * @author DimitriAndMathias
     * @param items The given layout's items
     * @param viewType The given layout's view type
     * @param callBack The given layout's call back
     * @version 2016.0501
     */
    public ListRecyclerViewAdapter(List<BasicItem> items, int viewType, CallBack callBack)
    {
        mValues = items;
        mViewType = viewType;
        mCallBack = callBack;

        initialize();
    }

    /**
     * Public constructor that saves the given items and view type
     *
     * @author DimitriAndMathias
     * @param items The given layout's items
     * @param viewType The given layout's view type
     * @version 2016.0501
     */
    public ListRecyclerViewAdapter(List<BasicItem> items, int viewType)
    {
        mValues = items;
        mViewType = viewType;

        initialize();
    }

    /**
     * Public constructor that saves the given view type
     *
     * @author DimitriAndMathias
     * @param viewType The given layout's view type
     * @version 2016.0501
     */
    public ListRecyclerViewAdapter(int viewType)
    {
        mValues = new ArrayList<>();
        mViewType = viewType;

        initialize();
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void clearList() { mValues.clear(); }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param item The given item
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void addItem(BasicItem item)
    {
        mValues.add(item);
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    private void initialize()
    {
        mViewTypeMap.put(R.integer.CARD_VIEW, R.layout.card_item);
        mViewTypeMap.put(R.integer.CARD_IMG_VIEW, R.layout.card_img_item);
        mViewTypeMap.put(R.integer.BASIC_VIEW, R.layout.basic_item);
        mViewTypeMap.put(R.integer.LIST_ITEM_VIEW, R.layout.list_item);
        mViewTypeMap.put(R.integer.COMMENT_VIEW, R.layout.comment_item);
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param parent The given view group
     * @param viewType The given view type
     * @version 2010.1105
     * @since 1.0
     * @return The view holder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = null;

        if (mViewTypeMap.containsKey(mViewType))
            view = LayoutInflater.from(parent.getContext())
                    .inflate(mViewTypeMap.get(mViewType), parent, false);

        return new ViewHolder(view);
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param holder The given holder
     * @param position The given positon
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
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

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The list's count
     */
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Class for the view holder
     *
     * @author DimitriAndMathias
     * @version 2016.0501
     * @since 2.0
     */
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // Private variables
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mDateView;
        public final ImageView mImageView;
        public final TextView mPosView;
        public BasicItem mItem;

        /**
         * Public constructor that saves the given view
         *
         * @author DimitriAndMathias
         * @param view The given view
         * @version 2016.0501
         */
        public ViewHolder(View view)
        {
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

