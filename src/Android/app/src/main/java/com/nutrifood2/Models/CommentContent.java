package com.nutrifood2.Models;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class CommentContent {
    /**
     * A map of sample (comment) items, by ID.
     */
    public static final Map<String, Comment> ITEM_MAP = new HashMap<String, Comment>();

    public static void addItem(Comment item) {

        if (!ITEM_MAP.containsKey(item.Id())) {
            ITEM_MAP.put(item.Id(), item);
        }
    }
}
