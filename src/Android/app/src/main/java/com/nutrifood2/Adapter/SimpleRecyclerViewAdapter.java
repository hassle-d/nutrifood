package com.nutrifood2.Adapter;

// Import needed to execute the code
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.nutrifood2.Models.Meal;
import com.nutrifood2.R;
import com.nutrifood2.Utils.CallBack;
import com.nutrifood2.Utils.Client;
import com.nutrifood2.Utils.ThreadManager;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Adapter for the simple recycler views
 *
 * @author DimitriAndMathias
 * @version 2016.0501
 * @since 2.0
 */
public class SimpleRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.ViewHolder>
{
    // Private variables
    private final List<Meal> mValues;
    private CallBack mCallBack;
    private Context mContext;

    /**
     * Public constructor that saves the given context, items and action
     *
     * @author DimitriAndMathias
     * @param context The given layout's context
     * @param items The given layout's items
     * @param action The given layout's action
     * @version 2016.0501
     */
    public SimpleRecyclerViewAdapter(Context context, List<Meal> items, CallBack action)
    {
        mValues = items;
        mCallBack = action;
        mContext = context;
    }

    /**
     * Public constructor that saves the given context and action
     *
     * @author DimitriAndMathias
     * @param context The given layout's context
     * @param action The given layout's action
     * @version 2016.0501
     */
    public SimpleRecyclerViewAdapter(Context context, CallBack action)
    {
        mValues = new ArrayList<>();
        mCallBack = action;
        mContext = context;
    }

    /**
     * Public constructor that saves the given context and items
     *
     * @author DimitriAndMathias
     * @param context The given layout's context
     * @param items The given layout's items
     * @version 2016.0501
     */
    public SimpleRecyclerViewAdapter(Context context, List<Meal> items)
    {
        mValues = items;
        mContext = context;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param item The given item
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void addItem(Meal item)
    {
        mValues.add(item);
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param parent The given view group
     * @param viewType The given view type
     * @version 2010.1105
     * @since 1.0
     * @return The View asked holder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_list_content, parent, false);
        return new ViewHolder(view);
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param holder The given view holder
     * @param position The given item's position
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        String name = holder.mItem.Name();
        final String img = holder.mItem.StrImage();
        Bitmap bitmap = holder.mItem.Bitmap();
        double rating = holder.mItem.Rating();

        if (name != null)
            holder.mIdView.setText(name);

        if (rating != 2.5)
            holder.mRatingView.setRating((float) rating);

        holder.mContentView.setImageResource(R.mipmap.ic_launcher);

        if (!ThreadManager.THREAD_LIST.contains(img)) {
            if (bitmap != null)
                holder.mContentView.setImageBitmap(bitmap);
            else if (img != null) {
                ThreadManager.addThreadToList(img);
                Client.get(mContext.getString(R.string.image_URL) + "/" + img, null, new FileAsyncHttpResponseHandler(mContext) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, File response) {
                        //downloadImage(response, holder, img);
                        Bitmap mIcon = null;
                        try {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                            mIcon = BitmapFactory.decodeStream(new FileInputStream(response), null, options);
                            holder.mItem.Bitmap(mIcon);
                            holder.mContentView.setImageBitmap(mIcon);
                            ThreadManager.THREAD_LIST.remove(img);
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                        Log.d("Meal", "onFailure");
                    }
                });
            }
        }

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final ImageView mContentView;
        public final RatingBar mRatingView;
        public Meal mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (ImageView) view.findViewById(R.id.image);
            mRatingView = (RatingBar) view.findViewById(R.id.ratingBar);
        }
    }
}
