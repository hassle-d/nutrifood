package com.nutrifood.cisner_d.nutrifood;

import android.graphics.Bitmap;

import com.nutrifood.cisner_d.nutrifood.adapter.TabViewAdapter;

public class DataHolder {
    public static String login;
    public static String password;
    public static String email;
    public static String firstname;
    public static String lastname;
    public static int age;
    public static String token;
    public static TabViewAdapter Adapter = null;
//    public static MessageAdapter MAdapter = null;

    static public void Clear()
    {
        login = null;
        password = null;
        email = null;
        firstname = null;
        lastname = null;
        age = 0;
        token = null;
//        Adapter = null;
//        MAdapter = null;
//        ModuleAdapter = null;
    }
}
