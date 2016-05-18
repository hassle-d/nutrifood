package com.nutrifood2.Models;

import java.util.ArrayList;

public class User {
    private String mUsername;
    private String mPassword;
    private String mEmail;
    private String mFirstname;
    private String mLastname;
    private int mAge;
    private String mDescription;
    private ArrayList<String> mSpecial;
    private ArrayList<String> mAllergy;

    public void User() {}

    public void User(String username, String password, String email, String firstname, String lastname, int age)
    {
        mUsername = username;
        mPassword = password;
        mEmail = email;
        mFirstname = firstname;
        mLastname = lastname;
        mAge = age;
    }

    public String Username()
    {
        return mUsername;
    }

    public void Username(String str)
    {
        mUsername = str;
    }

    public String Password()
    {
        return mPassword;
    }

    public void Password(String str)
    {
        mPassword = str;
    }

    public String Firstname()
    {
        return mFirstname;
    }

    public void Firstname(String str)
    {
        mFirstname = str;
    }

    public String Lastname()
    {
        return mLastname;
    }

    public void Lastname(String str)
    {
        mLastname = str;
    }

    public String Email()
    {
        return mEmail;
    }

    public void Email(String str)
    {
        mEmail = str;
    }

    public int Age()
    {
        return mAge;
    }

    public void Age(int i)
    {
        mAge = i;
    }

    public String Description()
    {
        return mDescription;
    }

    public void Description(String str)
    {
        mDescription = str;
    }

    public ArrayList<String> Spetial()
    {
        return mSpecial;
    }

    public void Spetial(ArrayList<String> list)
    {
        mSpecial = list;
    }

    public ArrayList<String> Allergy()
    {
        return mAllergy;
    }

    public void Allergy(ArrayList<String> list)
    {
        mAllergy = list;
    }
}
