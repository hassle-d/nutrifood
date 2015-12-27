package com.nutrifood.cisner_d.nutrifood;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import com.nutrifood.cisner_d.nutrifood.adapter.TabViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cz.msebera.android.httpclient.extras.Base64;

public class DataHolder {
    public static String login;
    public static String password;
    public static String email;
    public static String firstname;
    public static String lastname;
    public static int age;
    public static String token;
    public static TabViewAdapter Adapter = null;
    public static ArrayList<Meal> meals = null;
    public static ViewPager viewPager = null;
    public static final int HOME = 0;
    public static final int CATEGORY = 1;
    public static final int DISHES = 2;
    public static final int PROFIL = 3;
    public static final int CREATEDISHE = 4;
    public static final int DISHE = 5;
//    public static MessageAdapter MAdapter = null;

    static public <T> ArrayList<T> getArrayList(ArrayList<T> list, JSONArray array) throws JSONException
    {
        int lenght = array.length();
        for (int i = 0; i < lenght; ++i)
            list.add((T)array.get(i));
        return list;
    }

    /**
     * Encodes the byte array into base64 string
     *
     * @param imageByteArray - byte array
     * @return String a {@link java.lang.String}
     */
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }

    /**
     * Decodes the base64 string into byte array
     *
     * @param imageDataString - a {@link java.lang.String}
     * @return byte array
     */
    public static byte[] decodeImage(String imageDataString) {
        return Base64.decode(imageDataString, Base64.DEFAULT);
    }

    static public void Clear()
    {
        login = null;
        password = null;
        email = null;
        firstname = null;
        lastname = null;
        age = 0;
        token = null;
        meals.clear();
        meals = null;

//        Adapter = null;
//        MAdapter = null;
//        ModuleAdapter = null;
    }
}
