package com.nutrifood.cisner_d.nutrifood.Main.Pages.Category;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nutrifood.cisner_d.nutrifood.DataHolder;
import com.nutrifood.cisner_d.nutrifood.Main.CallBack;
import com.nutrifood.cisner_d.nutrifood.Main.Category;
import com.nutrifood.cisner_d.nutrifood.Main.Client;
import com.nutrifood.cisner_d.nutrifood.Main.Meal;
import com.nutrifood.cisner_d.nutrifood.Main.Pages.MealDetailFragment;
import com.nutrifood.cisner_d.nutrifood.Main.Pages.MealListFragment;
import com.nutrifood.cisner_d.nutrifood.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class CategoryMeals extends Fragment {
    private View meals_view;
    private boolean twoPane = false;
    private MealDetailFragment detailFragment;
    private MealListFragment listFragment;
    private FragmentManager fragmentManager;
    private Category _category;
    private boolean started = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        meals_view = inflater.inflate(R.layout.fragment_category_meals, container, false);

        fragmentManager = getFragmentManager();

        if (listFragment == null)
            listFragment = new MealListFragment();

        if (detailFragment == null)
            detailFragment = new MealDetailFragment();

        if (meals_view.findViewById(R.id.meal_detail_container) != null) {
            twoPane = true;
            fragmentManager.beginTransaction()
                    .replace(R.id.meal_detail_container, detailFragment)
                    .commit();
            fragmentManager.beginTransaction()
                    .replace(R.id.meal_list_container, listFragment)
                    .commit();
        }
        else {
            fragmentManager.beginTransaction()
                    .add(R.id.category_framelayout, listFragment)
                    .commit();
        }
        Log.d("CategoryMeals", "OnCreateVieaw");
        return meals_view;
    }

    public  void setCategory(Category category)
    {
        _category = category;
        if(started)
            getListMeals();
    }

    private Meal newMeal(JSONObject obj)
    {
        Meal meal = new Meal();
        try {
            meal.Author(obj.getString(getString(R.string.author_key)));
            meal.Category(obj.getString(getString(R.string.category_key)));
            meal.Name(obj.getString(getString(R.string.name_key)));
            meal.Difficulty(obj.getString(getString(R.string.difficulty_key)));
            meal.Id(obj.getString(getString(R.string.id_key)));
            meal.Author(obj.getString(getString(R.string.author_key)));
            meal.Cooktime(obj.getString(getString(R.string.cooktime_key)));
            meal.Description(obj.getString(getString(R.string.description_key)));
            meal.Ingredients(DataHolder.getArrayList(new ArrayList<String>(), obj.getJSONArray(getString(R.string.ingredients_key))));
            meal.Instruction(obj.getString(getString(R.string.instruction_key)));
            meal.Image(obj.getString(getString(R.string.image_key)), getActivity());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return meal;
    }

    private void getListMeals()
    {
        String url = getString(R.string.meals_URL) + getString(R.string.category_URL) + "/" + _category.Name();
        Client.get(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                int length = response.length();
                JSONObject obj;
                Log.d("SUCCESS", String.valueOf(length));
                try {
                    ArrayList<Meal> meals = new ArrayList<Meal>();
                    for (int i = 0; i < length; ++i) {
                        obj = response.getJSONObject(i);
                        if (obj != null)
                            meals.add(newMeal(obj));
                    }

                    listFragment.setList(meals, new CallBack() {
                        @Override
                        public void onItemClicked(Object item) {
                            if (twoPane == true) {
                                detailFragment.setMeal((Meal) item);
                                detailFragment.setData();
                            } else {
                                Log.d("ListMeals", "OnItemClicked");
                                detailFragment.setMeal((Meal) item);
                                fragmentManager.beginTransaction()
                                        .add(R.id.category_framelayout, detailFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                Log.d("SUCCESS", String.valueOf(object));
                ArrayList<Meal> meals = new ArrayList<Meal>();
                meals.add(newMeal(object));

                listFragment.setList(meals, new CallBack() {
                    @Override
                    public void onItemClicked(Object item) {
                        if (twoPane == true) {
                            detailFragment.setMeal((Meal) item);
                            detailFragment.setData();
                        } else {
                            Log.d("ListMeals", "OnItemClicked");
                            detailFragment.setMeal((Meal) item);
                            fragmentManager.beginTransaction()
                                    .replace(R.id.category_framelayout, detailFragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String string, Throwable throwable) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    String ret = errorResponse.getString(getString(R.string.error_key));
                    Log.d("ERROR : ", ret);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onStart()
    {
        started = true;
        if (_category != null)
            getListMeals();
        super.onStart();
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
