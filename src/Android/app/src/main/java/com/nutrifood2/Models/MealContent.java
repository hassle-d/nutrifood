package com.nutrifood2.Models;

import android.graphics.Bitmap;
import android.util.Log;

import com.nutrifood2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.client.cache.Resource;

public class MealContent {
    /**
     * A map of sample (meal) items, by ID.
     */
    public static final Map<String, Meal> ITEM_MAP = new HashMap<String, Meal>();

    public static void addItem(Meal item) {
        Log.d("addItem", item.Category());

        if (!ITEM_MAP.containsKey(item.Id())) {
            ITEM_MAP.put(item.Id(), item);
        }
    }
}
