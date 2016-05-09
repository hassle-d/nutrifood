package com.nutrifood2.Categories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nutrifood2.Adapter.ListRecyclerViewAdapter;
import com.nutrifood2.Meals.MealDetailActivity;
import com.nutrifood2.Meals.MealDetailFragment;
import com.nutrifood2.Meals.MealListActivity;
import com.nutrifood2.Meals.MealListFragment;
import com.nutrifood2.Models.BasicItem;
import com.nutrifood2.Models.Meal;
import com.nutrifood2.R;
import com.nutrifood2.Utils.CallBack;
import com.nutrifood2.Utils.RecyclerViewDecorator;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    private View category_view;
    private RecyclerView mRecyclerView = null;
    private List<Integer> mResources = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResources.add(R.drawable.medi_food);
        mResources.add(R.drawable.asian_food);
        mResources.add(R.drawable.vegetarian_food);
        mResources.add(R.drawable.french_food);
        mResources.add(R.drawable.british_food);
        mResources.add(R.drawable.indian_food);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        category_view = inflater.inflate(R.layout.fragment_category, container, false);

        mRecyclerView = (RecyclerView) category_view.findViewById(R.id.category_list);
        assert mRecyclerView != null;
        setupRecyclerView(mRecyclerView);

        return category_view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),
                getResources().getInteger(R.integer.GRID_COLUMNS));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        String[] categories = getResources().getStringArray(R.array.CategoryList);
        List<BasicItem> listItem = new ArrayList<>();

        for (int i = 0; i < categories.length; ++i)
            listItem.add(new BasicItem(categories[i], mResources.get(i)));

        recyclerView.addItemDecoration(new RecyclerViewDecorator(getResources()
                .getDimensionPixelSize(R.dimen.card_margin), getResources().getInteger(R.integer.GRID_COLUMNS)));

        recyclerView.setAdapter(new ListRecyclerViewAdapter(listItem, R.integer.CARD_IMG_VIEW, new CallBack() {
            @Override
            public void onItemClicked(Object item) {
                Context context = getContext();
                Intent intent = new Intent(context, MealListActivity.class);
                intent.putExtra(MealListActivity.ARG_CATEGORY_NAME, ((BasicItem)item).Name());

                context.startActivity(intent);
            }
        }));
    }
}
