package com.nutrifood.cisner_d.nutrifood;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import com.nutrifood.cisner_d.nutrifood.adapter.GridListAdapter;

import java.io.File;
import java.util.ArrayList;

public class Dishe extends Fragment {
    private View dishe_view;
    private TextView _mealname;
    private TextView _author;
    private TextView _category;
    private TextView _ingredient;
    private TextView _cooktime;
    private TextView _difficulty;
    private TextView _description;
    private TextView _instruction;
    private ImageView _image;
    private static Meal _meal;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dishe_view = inflater.inflate(R.layout.fragment_dishe, container, false);

        _mealname = (TextView)dishe_view.findViewById(R.id.mealname);
        _author = (TextView)dishe_view.findViewById(R.id.author);
        _category = (TextView)dishe_view.findViewById(R.id.category);
        _ingredient = (TextView)dishe_view.findViewById(R.id.ingredients);
        _difficulty = (TextView)dishe_view.findViewById(R.id.difficulty);
        _cooktime = (TextView)dishe_view.findViewById(R.id.cooktime);
        _description = (TextView)dishe_view.findViewById(R.id.description);
        _instruction = (TextView)dishe_view.findViewById(R.id.instruction);
        _image = (ImageView)dishe_view.findViewById(R.id.image);

        return dishe_view;
    }

    static public void setMeal(Meal meal)
    {
        _meal = meal;
    }

    @Override
    public void onStart()
    {
        setData();
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

    public void setData()
    {
        if (_meal == null)
            return;
        _mealname.setText(_meal.Name());
        _author.setText(_meal.Author());
        _category.setText(_meal.Category());

        _difficulty.setText(_meal.Difficulty());
        _cooktime.setText(_meal.Cooktime());
        _description.setText(_meal.Description());
        _instruction.setText(_meal.Instruction());

        Bitmap bitmap = _meal.Bitmap();

        if (bitmap != null)
            _image.setImageBitmap(bitmap);

        ArrayList<String> list = _meal.Ingredients();
        String ingredients = "";
        int lenght = list.size();
        for (int i = 0; i < lenght; ++i)
            ingredients = ingredients + list.get(i) + ", ";
        _ingredient.setText(ingredients);
    }
}
