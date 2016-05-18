package com.nutrifood2.Models;

// Import needed to execute the code
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Model for the comment's content
 *
 * @author DimitriAndMathias
 * @version 2016.0501
 * @since 2.0
 */
public class CommentContent
{
    /** Private variables
     * A map of sample (comment) items, by ID.
     */
    public static final Map<String, Comment> ITEM_MAP = new HashMap<String, Comment>();

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param item The given item
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public static void addItem(Comment item)
    {
        if (!ITEM_MAP.containsKey(item.Id())) {
            ITEM_MAP.put(item.Id(), item);
        }
    }
}
