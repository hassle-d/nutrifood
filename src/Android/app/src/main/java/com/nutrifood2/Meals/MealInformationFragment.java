package com.nutrifood2.Meals;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nutrifood2.Adapter.ListRecyclerViewAdapter;
import com.nutrifood2.Models.BasicItem;
import com.nutrifood2.Models.Meal;
import com.nutrifood2.Models.MealContent;
import com.nutrifood2.R;

import java.util.ArrayList;
import java.util.List;

public class MealInformationFragment extends Fragment {
    private static final String ARG_ITEM_ID = "item_id";

    private Meal mItem;

    public MealInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the meal content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = MealContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_meal_information, container, false);

        TextView description = (TextView) rootView.findViewById(R.id.description);
        description.setText(mItem.Description());

        TextView cooktime = (TextView) rootView.findViewById(R.id.cooktime);
        cooktime.setText(mItem.Cooktime());

        TextView category = (TextView) rootView.findViewById(R.id.category);
        category.setText(mItem.Category());

        return rootView;
    }
}
