package com.nutrifood2.Meals;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nutrifood2.Adapter.SimpleRecyclerViewAdapter;
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

/**
 * An activity representing a list of Meals. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MealDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MealListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public static final String ARG_CATEGORY_NAME = "category_name";
    private String mCategory = null;
    private RecyclerView recyclerView = null;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);

        mCategory = getIntent().getStringExtra(ARG_CATEGORY_NAME);
        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mCategory != null)
            toolbar.setTitle(mCategory);
        else
            toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = (RecyclerView) findViewById(R.id.meal_list);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);

        if (findViewById(R.id.meal_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        getListMeals();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        SimpleRecyclerViewAdapter adapter;
        /*
        if (mCategory != null)
            adapter = new SimpleRecyclerViewAdapter(this, MealContent.CATEGORY_MAP.get(mCategory));
        else
            adapter = new SimpleRecyclerViewAdapter(this, MealContent.ITEMS);
            */
        adapter = new SimpleRecyclerViewAdapter(this, new CallBack() {
            @Override
            public void onItemClicked(Object item) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(MealDetailFragment.ARG_ITEM_ID, ((Meal)item).Id());
                    MealDetailFragment fragment = new MealDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.meal_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(mContext, MealDetailActivity.class);
                    intent.putExtra(MealDetailFragment.ARG_ITEM_ID, ((Meal)item).Id());

                    mContext.startActivity(intent);
                }
            }
        });

        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new RecyclerViewDecorator(getResources()
                .getDimensionPixelSize(R.dimen.card_margin), getResources().getInteger(R.integer.GRID_COLUMNS)));
    }

    private Meal newMeal(JSONObject obj)
    {
        Meal meal = null;
        try {
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
            meal.Description(obj.getString(getString(R.string.description_key)));
            meal.Ingredients(DataHolder.getArrayList(new ArrayList<String>(), obj.getJSONArray(getString(R.string.ingredients_key))));
            meal.Instruction(DataHolder.getArrayList(new ArrayList<String>(), obj.getJSONArray(getString(R.string.instruction_key))));
            meal.StrImage(obj.getString(getString(R.string.image_key)));
            //           meal.Image(obj.getString(getString(R.string.image_key)), getActivity());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return meal;
    }

    private void getListMeals()
    {
        String url;
        if (mCategory != null)
            url = getString(R.string.meals_URL) + getString(R.string.category_URL) + "/" + mCategory;
        else
            url = getString(R.string.meals_URL);
        Client.get(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                int length = response.length();
                JSONObject obj;
                SimpleRecyclerViewAdapter adapter;
                adapter = (SimpleRecyclerViewAdapter) recyclerView.getAdapter();
                Log.d("SUCCESS", String.valueOf(length));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            //NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
