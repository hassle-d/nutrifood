package com.nutrifood.cisner_d.nutrifood.Main.Pages.Category;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nutrifood.cisner_d.nutrifood.Main.CallBack;
import com.nutrifood.cisner_d.nutrifood.Main.Category;
import com.nutrifood.cisner_d.nutrifood.Client;
import com.nutrifood.cisner_d.nutrifood.R;
import com.nutrifood.cisner_d.nutrifood.adapter.GridCategoryListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class Categories extends Fragment {
    private View category_view;
    private ArrayList<Category> categories =  new ArrayList<Category>();
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private CategoryMeals mealsFragment;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        category_view = inflater.inflate(R.layout.fragment_category, container, false);

        recyclerView = (RecyclerView) category_view.findViewById(R.id.category_list);
//        layoutManager = new AutoFitLayoutManager(getActivity(), 90);
        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new GridCategoryListAdapter(getActivity(), inflater));

        fragmentManager = getFragmentManager();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        categories.add(new Category("French Food",BitmapFactory.decodeResource(getResources(), R.drawable.french_food, options)));
        categories.add(new Category("Indian Food",BitmapFactory.decodeResource(getResources(), R.drawable.indian_food, options)));
        categories.add(new Category("Asian Food",BitmapFactory.decodeResource(getResources(), R.drawable.asian_food, options)));
        categories.add(new Category("British Food",BitmapFactory.decodeResource(getResources(), R.drawable.british_food, options)));
        categories.add(new Category("Vegetarian Food",BitmapFactory.decodeResource(getResources(), R.drawable.vegetarian_food, options)));
        categories.add(new Category("Medi Food",BitmapFactory.decodeResource(getResources(), R.drawable.medi_food, options)));

        if (mealsFragment == null)
            mealsFragment = new CategoryMeals();
        return category_view;
    }

    @Override
    public void onStart()
    {
        ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getListCategory();
        } else {
        }
        super.onStart();
    }

    private Category newCategory(JSONObject obj)
    {
        Category category = new Category();
        try {
            category.Id(obj.getString(getString(R.string.id_key)));
            category.Name(obj.getString(getString(R.string.name_key)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return category;
    }

    private void getListCategory()
    {
        Client.get(getString(R.string.category_URL), null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                int length = response.length();
                JSONObject obj;
                Log.d("SUCCESS", String.valueOf(length));
                try {
                    for (int i = 0; i < length; ++i) {
                        obj = response.getJSONObject(i);
                        if (obj != null)
                            categories.add(newCategory(obj));
                    }

                    ((GridCategoryListAdapter) recyclerView.getAdapter()).setItemsList(categories, new CallBack() {
                        @Override
                        public void onItemClicked(Object item) {
                            Log.d("Categories", "OnItemClicked");
                            mealsFragment.setCategory((Category) item);
                            fragmentManager.beginTransaction()
                                    .add(R.id.category_root, mealsFragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                Log.d("SUCCESS", "TWO");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String string, Throwable throwable) {
                Log.d("ERROR", "ONE");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    String ret = errorResponse.getString(getString(R.string.error_key));
                    Log.d("ERROR", ret);
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
