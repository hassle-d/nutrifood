package com.nutrifood.cisner_d.nutrifood.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.nutrifood.cisner_d.nutrifood.Client;
import com.nutrifood.cisner_d.nutrifood.Main.CallBack;
import com.nutrifood.cisner_d.nutrifood.Main.Meal;
import com.nutrifood.cisner_d.nutrifood.R;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class GridListAdapter extends RecyclerView.Adapter<GridListAdapter.MyViewHolder> {
    Context _context;
    LayoutInflater _inflater;
    List<Meal> _items = Collections.emptyList();
    CallBack _callback;


    public GridListAdapter(Context context, LayoutInflater inflater) {
        _inflater = inflater;
        _context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = _inflater.inflate(R.layout.grid_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Meal current = _items.get(position);
        File image = current.Image();
        final String img = current.StrImage();
        Bitmap bitmap = current.Bitmap();
        String name = current.Name();

        if (name != null)
            holder.titleView.setText(name);
        holder.iconView.setImageResource(R.mipmap.ic_launcher);

        if (img != null && bitmap == null) {
            Client.get(_context.getString(R.string.image_URL) + "/" + img, null, new FileAsyncHttpResponseHandler(_context) {
                @Override
                public void onSuccess(int statusCode, Header[] headers, File response) {
                    new DownloadImageTask(holder.iconView, current, _context).execute(response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                    Log.d("Meal", "onFailure");
                }
            });
        }
        else if (bitmap != null)
            holder.iconView.setImageBitmap(bitmap);
        else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            holder.iconView.setImageBitmap(BitmapFactory.decodeResource(_context.getResources(), R.drawable.frenchfoodporn, options));
        }
    }

    @Override
    public int getItemCount() {
        return _items.size();
    }

    public void setItemsList(List<Meal> list, CallBack callback)
    {
        Log.d("setItemsList", String.valueOf(list.size()));
        _items = list;
        _callback = callback;
        this.notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleView;
        ImageView iconView;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            titleView = (TextView) itemView.findViewById(R.id.title);
            iconView = (ImageView) itemView.findViewById(R.id.icon);
        }

        @Override
        public void onClick(View v) {
            Log.d("MyViewHolder", "OnClick");
            if (_callback != null)
                _callback.onItemClicked(_items.get(this.getPosition()));
        }
    }

    public class DownloadImageTask extends AsyncTask<File, Void, Bitmap> {
        ImageView bmImage;
        Meal      object;
        Context   context;

        public DownloadImageTask(ImageView bmImage, Meal obj, Context context) {
            this.bmImage = bmImage;
            object = obj;
            this.context = context;
        }

        protected Bitmap doInBackground(File... files){
            File file = files[0];
            Bitmap mIcon = null;
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                mIcon = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
                object.Bitmap(mIcon);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
