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

import com.nutrifood.cisner_d.nutrifood.DataHolder;
import com.nutrifood.cisner_d.nutrifood.Dishe;
import com.nutrifood.cisner_d.nutrifood.Meal;
import com.nutrifood.cisner_d.nutrifood.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.extras.Base64;


public class GridListAdapter extends RecyclerView.Adapter<GridListAdapter.MyViewHolder> {
    Context _context;
    LayoutInflater _inflater;
    List<Meal> _items = Collections.emptyList();

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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Meal current = _items.get(position);
        File image = current.Image();
        Bitmap bitmap = current.Bitmap();
        String name = current.Name();

        if (name != null)
            holder.titleView.setText(name);
        holder.iconView.setImageResource(R.drawable.ic_launcher);

        if (image != null && bitmap == null)
            new DownloadImageTask(holder.iconView, current).execute(image);
        else if (bitmap != null)
            holder.iconView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return _items.size();
    }

    public void setItemsList(List<Meal> list)
    {
        Log.d("setItemsList", String.valueOf(list.size()));
        _items = list;
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
            Dishe.setMeal(DataHolder.meals.get(this.getPosition()));
            DataHolder.viewPager.setCurrentItem(DataHolder.DISHE);
        }
    }

    public class DownloadImageTask extends AsyncTask<File, Void, Bitmap> {
        ImageView   bmImage;
        Meal        object;

        public DownloadImageTask(ImageView bmImage, Meal obj) {
            this.bmImage = bmImage;
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
/*
        protected Bitmap doInBackground(String... str){
            String encodedImage = str[0];
            Bitmap mIcon = null;
            try {
                byte[] decodedString = DataHolder.decodeImage(encodedImage);
                mIcon = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }
*/
/*
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }
*/
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
 /*
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, null);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        titleView.setText( items.get(position).Name() );
        iconView.setImageResource(items.get(position).Image());

        return view;
    }
*/
}
