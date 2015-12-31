package com.nutrifood.cisner_d.nutrifood.Main.Pages;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nutrifood.cisner_d.nutrifood.Main.ListItem;
import com.nutrifood.cisner_d.nutrifood.Main.Meal;
import com.nutrifood.cisner_d.nutrifood.R;
import com.nutrifood.cisner_d.nutrifood.adapter.ListAdapter;

import java.util.ArrayList;

enum DetailInfo {
    AUTHOR,
    CATEGORY,
    DIFFICULTY,
    INGREDIENTS,
    COOKTIME,
    DESCRIPTION,
    INSTRUCTION,
    NUTRITIONFACT
}

public class MealDetailFragment extends Fragment {
    private View meal_detail_view;

    private TextView _mealname;
    private ImageView _image;
    private Meal _meal = null;
    private ArrayList<ListItem> items;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        meal_detail_view = inflater.inflate(R.layout.fragment_meal_detail, container, false);

        _mealname = (TextView)meal_detail_view.findViewById(R.id.mealname);
        if (_mealname == null)
            Log.d("MealDetailFragment","_meal = null");
        _image = (ImageView)meal_detail_view.findViewById(R.id.image);

        items = new ArrayList<ListItem>();

        items.add(new ListItem("Author"));
        items.add(new ListItem("Category"));
        items.add(new ListItem("Difficulty"));
        items.add(new ListItem("Ingredients"));
        items.add(new ListItem("Cooktime"));
        items.add(new ListItem("Description"));
        items.add(new ListItem("Instruction"));
        items.add(new ListItem("NutritionFact"));

        listView = (ListView)meal_detail_view.findViewById(R.id.list_data);
        ListAdapter adapter = new ListAdapter(getActivity(), items);
        listView.setAdapter(adapter);

        return meal_detail_view;
    }

    public void setMeal(Meal meal) { _meal = meal; }

    @Override
    public void onStart()
    {
        super.onStart();
        setData();
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

    public void setData()
    {
        Log.d("MealDetailFragment", "SetData");
        if (_meal == null)
            return;

        try {
            _mealname.setText(_meal.Name());

            items.get(DetailInfo.AUTHOR.ordinal())._subtitle = _meal.Author();
            items.get(DetailInfo.CATEGORY.ordinal())._subtitle = _meal.Category();
            items.get(DetailInfo.COOKTIME.ordinal())._subtitle = _meal.Cooktime();
            items.get(DetailInfo.DIFFICULTY.ordinal())._subtitle = _meal.Difficulty();
            items.get(DetailInfo.DESCRIPTION.ordinal())._subtitle = _meal.Description();
            items.get(DetailInfo.INSTRUCTION.ordinal())._subtitle = _meal.Instruction();


            Bitmap bitmap = _meal.Bitmap();

            if (bitmap != null)
                _image.setImageBitmap(bitmap);

            ArrayList<String> list = _meal.Ingredients();
            String ingredients = "";
            int lenght = list.size();
            for (int i = 0; i < lenght; ++i)
                ingredients = ingredients + list.get(i) + ", ";

            items.get(DetailInfo.INGREDIENTS.ordinal())._subtitle = ingredients;

            ((ListAdapter)listView.getAdapter()).setList(items);
        } catch (Exception e) {
        }
    }
}
