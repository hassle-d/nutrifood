package com.nutrifood2.Adapter;

// Import needed to execute the code
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.nutrifood2.R;

import java.util.ArrayList;

/**
 * Adapter for the custom lists
 *
 * @author DimitriAndMathias
 * @version 2016.0501
 * @since 2.0
 */
public class CustomListAdapter extends BaseAdapter implements ListAdapter
{
    // Private variables
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    /**
     * Public constructor that saves the given context
     *
     * @author DimitriAndMathias
     * @param context The given layout's context
     * @version 2016.0501
     */
    public CustomListAdapter(Context context) {
        this.context = context;
    }

    /**
     * Public constructor that saves the given context and list
     *
     * @author DimitriAndMathias
     * @param list The given items' list
     * @param context The given layout's context
     * @version 2016.0501
     * @since 2.0
     */
    public CustomListAdapter(ArrayList<String> list, Context context)
    {
        this.list = list;
        this.context = context;
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
    public void addItem(String item)
    {
        this.list.add(item);
        this.notifyDataSetChanged();
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The list of items
     */
    public ArrayList<String> getList() {
        return list;
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
    public int getCount() {
        return list.size();
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param pos The given item's position
     * @version 2010.1105
     * @since 1.0
     * @return The asked item
     */
    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param pos The given item's id
     * @version 2010.1105
     * @since 1.0
     * @return The asked item
     */
    @Override
    public long getItemId(int pos) {
        return 0;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param position The given position
     * @param convertView The given convert view
     * @param parent The given parent view
     * @version 2010.1105
     * @since 1.0
     * @return The asked view
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.deletable_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.text_item);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        ImageButton deleteBtn = (ImageButton)view.findViewById(R.id.remove_button);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
