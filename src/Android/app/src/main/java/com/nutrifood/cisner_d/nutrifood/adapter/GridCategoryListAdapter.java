package com.nutrifood.cisner_d.nutrifood.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nutrifood.cisner_d.nutrifood.Main.CallBack;
import com.nutrifood.cisner_d.nutrifood.Main.Category;
import com.nutrifood.cisner_d.nutrifood.R;

import java.util.Collections;
import java.util.List;

public class GridCategoryListAdapter extends RecyclerView.Adapter<GridCategoryListAdapter.MyViewHolder> {

    Context         _context;
    LayoutInflater  _inflater;
    List<Category>  _items = Collections.emptyList();
    CallBack        _callback;


    public GridCategoryListAdapter(Context context, LayoutInflater inflater) {
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
        Category current = _items.get(position);
        String name = current.Name();
        Bitmap bmp = current.Bitmap();

        if (name != null)
            holder.titleView.setText(name);
        if (bmp != null)
            holder.iconView.setImageBitmap(bmp);
    }

    @Override
    public int getItemCount() {
        return _items.size();
    }

    public void setItemsList(List<Category> list, CallBack callback)
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
            Log.d("Category MyViewHolder", _items.get(this.getPosition()).Name());

            if (_callback != null)
                _callback.onItemClicked(_items.get(this.getPosition()));
        }
    }
}

