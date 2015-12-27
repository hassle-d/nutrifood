package com.nutrifood.cisner_d.nutrifood;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nutrifood.cisner_d.nutrifood.adapter.GridListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;

import java.io.File;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

public class Dishes extends Fragment {
    private View dishes_view;
    private RecyclerView recyclerView;
//    private LinearLayoutManager layoutManager;
    private StaggeredGridLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dishes_view = inflater.inflate(R.layout.fragment_dishes, container, false);

        recyclerView = (RecyclerView) dishes_view.findViewById(R.id.recycler_view);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
//        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new GridListAdapter(getActivity(), inflater));

        return dishes_view;
    }

    @Override
    public void onStart()
    {
        ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getListMeals();
        } else {
        }
        super.onStart();
    }

    private Meal newMeal(JSONObject obj)
    {
        Meal meal = new Meal();
        try {
            meal.Author(obj.getString(getString(R.string.author_key)));
            meal.Category(obj.getString(getString(R.string.category_key)));
            meal.Name(obj.getString(getString(R.string.mealname_key)));
            meal.Difficulty(obj.getString(getString(R.string.difficulty_key)));
            meal.Id(obj.getString(getString(R.string.meal_id_key)));
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
        Client.get(getString(R.string.meals_URL), null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                int length = response.length();
                JSONObject obj;
                Log.d("SUCCESS", String.valueOf(length));
                try {
                    ArrayList<Meal> meals = new ArrayList<Meal>();
                    for (int i = 0; i < length; ++i)
                    {
                        obj = response.getJSONObject(i);
                        if (obj != null)
                            meals.add(newMeal(obj));
                    }
                    DataHolder.meals = meals;
                    ((GridListAdapter)recyclerView.getAdapter()).setItemsList(DataHolder.meals);
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
