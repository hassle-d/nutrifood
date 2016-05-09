package com.nutrifood2.Models;

import java.util.ArrayList;

public class User {
    private String _username;
    private String _password;
    private String _email;
    private String _firstname;
    private String _lastname;
    private int _age;
    private String _description;
    private ArrayList<String> _special;
    private ArrayList<String> _allergy;

    public void User() {}

    public void User(String username, String password, String email, String firstname, String lastname, int age)
    {
        _username = username;
        _password = password;
        _email = email;
        _firstname = firstname;
        _lastname = lastname;
        _age = age;
    }

    public String Username()
    {
        return _username;
    }

    public void Username(String str)
    {
        _username = str;
    }

    public String Password()
    {
        return _password;
    }

    public void Password(String str)
    {
        _password = str;
    }

    public String Firstname()
    {
        return _firstname;
    }

    public void Firstname(String str)
    {
        _firstname = str;
    }

    public String Lastname()
    {
        return _lastname;
    }

    public void Lastname(String str)
    {
        _lastname = str;
    }

    public String Email()
    {
        return _email;
    }

    public void Email(String str)
    {
        _email = str;
    }

    public int Age()
    {
        return _age;
    }

    public void Age(int i)
    {
        _age = i;
    }

    public String Description()
    {
        return _description;
    }

    public void Description(String str)
    {
        _description = str;
    }

    public ArrayList<String> Spetial()
    {
        return _special;
    }

    public void Spetial(ArrayList<String> list)
    {
        _special = list;
    }

    public ArrayList<String> Allergy()
    {
        return _allergy;
    }

    public void Allergy(ArrayList<String> list)
    {
        _allergy = list;
    }
}
