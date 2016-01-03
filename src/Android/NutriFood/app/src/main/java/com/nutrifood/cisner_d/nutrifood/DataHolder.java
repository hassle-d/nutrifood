package com.nutrifood.cisner_d.nutrifood;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.extras.Base64;

public class DataHolder {
    public static String token;
    public static String[] category = { "French", "Asian", "British", "Indian", "Medi" };
    public static String[] difficulty = { "easy", "middle", "hard" };

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
     * @return String a {@link String}
     */
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }

    /**
     * Decodes the base64 string into byte array
     *
     * @param imageDataString - a {@link String}
     * @return byte array
     */
    public static byte[] decodeImage(String imageDataString) {
        return Base64.decode(imageDataString, Base64.DEFAULT);
    }
}
