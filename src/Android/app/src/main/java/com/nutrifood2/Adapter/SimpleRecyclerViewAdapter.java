package com.nutrifood2.Adapter;

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

public class SimpleRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.ViewHolder> {

    private final List<Meal> mValues;
    private CallBack mCallBack;
    private Context mContext;

    public SimpleRecyclerViewAdapter(Context context, List<Meal> items, CallBack action) {
        mValues = items;
        mCallBack = action;
        mContext = context;
    }

    public SimpleRecyclerViewAdapter(Context context, CallBack action) {
        mValues = new ArrayList<>();
        mCallBack = action;
        mContext = context;
    }

    public SimpleRecyclerViewAdapter(Context context, List<Meal> items) {
        mValues = items;
        mContext = context;
    }

    public void addItem(Meal item)
    {
        mValues.add(item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_list_content, parent, false);
        return new ViewHolder(view);
    }

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
