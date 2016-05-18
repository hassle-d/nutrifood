package com.nutrifood2.Models;

// Import needed to execute the code
import android.graphics.Bitmap;
import android.util.Log;

import com.nutrifood2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.client.cache.Resource;

/**
 * Model for the meal's content
 *
 * @author DimitriAndMathias
 * @version 2016.0501
 * @since 2.0
 */
public class MealContent
{
    /** Private variables
     * A map of sample (meal) items, by ID.
     */
    public static final Map<String, Meal> ITEM_MAP = new HashMap<String, Meal>();

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param item The given meal
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public static void addItem(Meal item)
    {
        Log.d("addItem", item.Category());

        if (!ITEM_MAP.containsKey(item.Id())) {
            ITEM_MAP.put(item.Id(), item);
        }
    }
}
