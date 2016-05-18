package com.nutrifood2.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nutrifood2.Adapter.SimpleRecyclerViewAdapter;
import com.nutrifood2.Meals.MealDetailActivity;
import com.nutrifood2.Meals.MealDetailFragment;
import com.nutrifood2.Models.Meal;
import com.nutrifood2.Models.MealContent;
import com.nutrifood2.R;
import com.nutrifood2.Utils.CallBack;
import com.nutrifood2.Utils.Client;
import com.nutrifood2.Utils.DataHolder;
import com.nutrifood2.Utils.RecyclerViewDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class                HomeFragment extends Fragment {

    private View            home_view;
    private RecyclerView    recyclerView;

    private final int       MAX_MEALS = 6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        this.home_view = inflater.inflate(R.layout.fragment_home, container, false);
        this.recyclerView = (RecyclerView) this.home_view.findViewById(R.id.home_recyclerview);

        assert this.recyclerView != null;
        setupRecyclerView(this.recyclerView);

        getListMeals();

        return home_view;
    }

    @Override
    public void onStart() { super.onStart(); }

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

    private void            setupRecyclerView(@NonNull RecyclerView recyclerView)
    {
        GridLayoutManager   layoutManager;

        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SimpleRecyclerViewAdapter(getActivity(),new CallBack() {
            @Override
            public void onItemClicked(Object item) {

                Context context = getContext();
                Intent intent = new Intent(context, MealDetailActivity.class);

                intent.putExtra(MealDetailFragment.ARG_ITEM_ID, ((Meal)item).Id());
                context.startActivity(intent);
            }
        }));

        recyclerView.addItemDecoration(new RecyclerViewDecorator(
                getResources().getDimensionPixelSize(R.dimen.card_margin),
                getResources().getInteger(R.integer.GRID_COLUMNS)));
    }

    private void getListMeals()
    {
        Client.get(getString(R.string.meals_URL), null, new JsonHttpResponseHandler()
        {
            @Override
            public void                     onSuccess(int statusCode, Header[] headers,
                                                      JSONArray response)
            {
                SimpleRecyclerViewAdapter   adapter;
                int                         length;
                JSONObject                  obj;

                adapter = (SimpleRecyclerViewAdapter) recyclerView.getAdapter();
                length = response.length();
                length = length > MAX_MEALS ? MAX_MEALS : length;
                try {
                    for (int i = length - 1; i >= 0; --i) {
                        obj = response.getJSONObject(i);
                        if (obj != null) {
                            Meal item = newMeal(obj);
                            MealContent.addItem(item);
                            adapter.addItem(item);
                        }

                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) { }

            @Override
            public void onFailure(int statusCode, Header[] headers, String string,
                                  Throwable throwable) { }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) { }
        });
    }

    private Meal    newMeal(JSONObject obj) {
        String id;
        Meal meal;

        meal = null;
        try {
            id = obj.getString(getString(R.string.id_key));

            if (MealContent.ITEM_MAP.containsKey(id))
                meal = MealContent.ITEM_MAP.get(id);
            else
                meal = new Meal();

            meal.Author(obj.getString(getString(R.string.author_key)));
            if (meal.Category() != obj.getString(getString(R.string.category_key)))
                meal.OldCategory(meal.Category());

            meal.Category(obj.getString(getString(R.string.category_key)));
            meal.Name(obj.getString(getString(R.string.name_key)));
            meal.Difficulty(obj.getString(getString(R.string.difficulty_key)));
            meal.Id(obj.getString(getString(R.string.id_key)));
            meal.Author(obj.getString(getString(R.string.author_key)));
            meal.Cooktime(obj.getString(getString(R.string.cooktime_key)));
            meal.Description(obj.getString(getString(R.string.description_key)));

            meal.Ingredients(DataHolder.getArrayList(new ArrayList<String>(),
                    obj.getJSONArray(getString(R.string.ingredients_key))));
            meal.Instruction(DataHolder.getArrayList(new ArrayList<String>(),
                    obj.getJSONArray(getString(R.string.instruction_key))));

            meal.StrImage(obj.getString(getString(R.string.image_key)));
            meal.Rating(obj.getDouble(getString(R.string.rating_key)));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return meal;
    }
}
