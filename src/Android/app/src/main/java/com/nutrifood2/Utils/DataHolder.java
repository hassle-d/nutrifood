package com.nutrifood2.Utils;

import com.nutrifood2.Models.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DataHolder {
    public static String token = null;
    public static User user = null;

    static public <T> ArrayList<T> getArrayList(ArrayList<T> list, JSONArray array) throws JSONException
    {
        int lenght = array.length();
        for (int i = 0; i < lenght; ++i)
            list.add((T)array.get(i));
        return list;
    }

    static public <T> JSONArray getJsonArray(ArrayList<T> list, JSONArray array) throws JSONException
    {
        int lenght = list.size();
        for (int i = 0; i < lenght; ++i)
            array.put(list.get(i));
        return array;
    }
}
