package com.nutrifood.cisner_d.nutrifood.Main.Pages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nutrifood.cisner_d.nutrifood.Main.CallBack;
import com.nutrifood.cisner_d.nutrifood.Main.Meal;
import com.nutrifood.cisner_d.nutrifood.R;
import com.nutrifood.cisner_d.nutrifood.adapter.GridListAdapter;

import java.util.ArrayList;

public class MealListFragment extends Fragment {
    private View meals_view;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager layoutManager;
    private ArrayList<Meal> _meals = null;
    private CallBack _callback = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        meals_view = inflater.inflate(R.layout.fragment_list_meals, container, false);

        recyclerView = (RecyclerView) meals_view.findViewById(R.id.meals_list);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new GridListAdapter(getActivity(), inflater));

        return meals_view;
    }

    @Override
    public void onStart()
    {
        if (_meals != null && _callback != null)
            ((GridListAdapter) recyclerView.getAdapter()).setItemsList(_meals, _callback);
        super.onStart();
    }

    public void setList(ArrayList<Meal> list, CallBack callback)
    {
        _meals = list;
        _callback = callback;
        ((GridListAdapter) recyclerView.getAdapter()).setItemsList(list, callback);
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
