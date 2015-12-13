package com.nutrifood.cisner_d.nutrifood;

import android.graphics.Bitmap;

public class DataHolder {
    public static String login;
    public static String password;
    public static String email;
    public static String firstname;
    public static String lastname;
    public static String age;
    private String token;
    private static DataHolder instence;
//    public static TabViewAdapter Adapter = null;
//    public static MessageAdapter MAdapter = null;
//    public static com.nutrifood.cisner_d.nutrifood.adapter.ModuleAdapter ModuleAdapter = null;
    public DataHolder()
    {
        token = null;
    }

    public static DataHolder getInstence()
    {
        if (instence == null)
            instence = new DataHolder();
        return instence;
    }

    public String Token() { return token; }

    public void Token(String t) { token = t; }

    public void Clear()
    {
        login = null;
        password = null;
        token = null;
//        Adapter = null;
//        MAdapter = null;
//        ModuleAdapter = null;
    }
}
