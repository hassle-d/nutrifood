package com.nutrifood2.Meals;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nutrifood2.Adapter.SimpleRecyclerViewAdapter;
import com.nutrifood2.CreateMealActivity;
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

public class MealListFragment extends Fragment {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private RecyclerView recyclerView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View meals_view = inflater.inflate(R.layout.fragment_meal_list, container, false);

        recyclerView = (RecyclerView) meals_view.findViewById(R.id.meal_list);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);

        FloatingActionButton fab = (FloatingActionButton) meals_view.findViewById(R.id.action_add_meal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                Intent intent = new Intent(context, CreateMealActivity.class);

                context.startActivity(intent);
            }
        });

        if (meals_view.findViewById(R.id.meal_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        getListMeals();

        return meals_view;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SimpleRecyclerViewAdapter(getActivity(),new CallBack() {
            @Override
            public void onItemClicked(Object item) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(MealDetailFragment.ARG_ITEM_ID, ((Meal)item).Id());
                    MealDetailFragment fragment = new MealDetailFragment();
                    fragment.setArguments(arguments);
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.meal_detail_container, fragment)
                            .commit();
                } else {
                    Context context = getContext();
                    Intent intent = new Intent(context, MealDetailActivity.class);
                    intent.putExtra(MealDetailFragment.ARG_ITEM_ID, ((Meal)item).Id());

                    context.startActivity(intent);
                }
            }
        }));
        recyclerView.addItemDecoration(new RecyclerViewDecorator(getResources()
                .getDimensionPixelSize(R.dimen.card_margin), getResources().getInteger(R.integer.GRID_COLUMNS)));
    }

    private Meal newMeal(JSONObject obj)
    {
        Meal meal = null;
        try {
            Log.d("newMeal", obj.toString());
            String id = obj.getString(getString(R.string.id_key));
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
            meal.Rating(obj.getInt(getString(R.string.rating_key)));
            meal.Description(obj.getString(getString(R.string.description_key)));
            meal.Ingredients(DataHolder.getArrayList(new ArrayList<String>(), obj.getJSONArray(getString(R.string.ingredients_key))));
            meal.Instruction(DataHolder.getArrayList(new ArrayList<String>(), obj.getJSONArray(getString(R.string.instruction_key))));
            meal.StrImage(obj.getString(getString(R.string.image_key)));
            meal.Rating(obj.getInt(getString(R.string.rating_key)));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return meal;
    }

    private void getListMeals()
    {
        Client.get(getString(R.string.meals_URL), null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                int length = response.length();
                JSONObject obj;
                SimpleRecyclerViewAdapter adapter;
                adapter = (SimpleRecyclerViewAdapter) recyclerView.getAdapter();
                Log.d("SUCCESS", response.toString());
                try {
                    for (int i = 0; i < length; ++i) {
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
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                Log.d("SUCCESS : ", "TWO");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String string, Throwable throwable) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });
    }
}
