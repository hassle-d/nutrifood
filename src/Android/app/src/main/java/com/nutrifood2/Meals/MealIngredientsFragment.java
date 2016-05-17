package com.nutrifood2.Meals;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nutrifood2.Adapter.ListRecyclerViewAdapter;
import com.nutrifood2.Models.BasicItem;
import com.nutrifood2.Models.Meal;
import com.nutrifood2.Models.MealContent;
import com.nutrifood2.R;

import java.util.ArrayList;
import java.util.List;

public class MealIngredientsFragment extends Fragment {

    private static final String ARG_ITEM_ID = "item_id";

    private Meal mItem;

    public MealIngredientsFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_meal_ingredients, container, false);

        // Show the meal content
        if (mItem != null)
        {
            List<BasicItem> list = new ArrayList<BasicItem>();
            List<String> arrayList = mItem.Ingredients();

            for (int i = 0; i < arrayList.size(); ++i)
                list.add(new BasicItem(arrayList.get(i)));

            RecyclerView detail_recyclerview = (RecyclerView)rootView.findViewById(R.id.detail_recyclerview);
            detail_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            detail_recyclerview.setAdapter(new ListRecyclerViewAdapter(list, R.integer.LIST_ITEM_VIEW));
        }

        return rootView;
    }
}
