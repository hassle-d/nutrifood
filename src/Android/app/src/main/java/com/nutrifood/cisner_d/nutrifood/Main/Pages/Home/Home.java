package com.nutrifood.cisner_d.nutrifood.Main.Pages.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nutrifood.cisner_d.nutrifood.Client;
import com.nutrifood.cisner_d.nutrifood.DataHolder;
import com.nutrifood.cisner_d.nutrifood.Main.CallBack;
import com.nutrifood.cisner_d.nutrifood.Main.Meal;
import com.nutrifood.cisner_d.nutrifood.Main.Pages.MealDetailFragment;
import com.nutrifood.cisner_d.nutrifood.Main.Pages.MealListFragment;
import com.nutrifood.cisner_d.nutrifood.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Home extends Fragment {
    private View home_view;
    private EditText search;
    private MealDetailFragment detailFragment;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        home_view = inflater.inflate(R.layout.fragment_home, container, false);

        search = (EditText)home_view.findViewById(R.id.search_bar);

        fragmentManager = getFragmentManager();

        detailFragment = (MealDetailFragment)fragmentManager.findFragmentByTag("home_detail");
        if (detailFragment == null)
            detailFragment = new MealDetailFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.home_detail, detailFragment)
                .commit();

        search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    onSearch();
                    return true;
                }
                return false;
            }
        });

        return home_view;
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
            meal.StrImage(obj.getString(getString(R.string.image_key)));
            //           meal.Image(obj.getString(getString(R.string.image_key)), getActivity());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return meal;
    }

    private void onSearch()
    {
            Client.get(getString(R.string.search_URL) + "/" + search.getText().toString(), null, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    int length = response.length();
                    JSONObject obj;
                    Log.d("SUCCESS", String.valueOf(length));
                    ArrayList<Meal> meals = new ArrayList<Meal>();
                    for (int i = 0; i < length; ++i) {
                        try {
                            obj = response.getJSONObject(i);
                            if (obj != null)
                                meals.add(newMeal(obj));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (meals.size() > 0) {
                        detailFragment.setMeal(meals.get(0));
                        detailFragment.setData();
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

    @Override
    public void onStart()
    {
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
