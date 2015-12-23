package com.nutrifood.cisner_d.nutrifood;

import java.util.ArrayList;

public class Meal {
    private String _author;
    private String _name;
    private String _description;
    private String _instruction;
    private String _difficulty;
    private String _category;
    private String _cooktime;
    private ArrayList<String> _ingredients;

    void Meal() {}

    public String Author()
    {
        return _author;
    }

    public void Author(String str)
    {
        _author = str;
    }

    public String Name()
    {
        return _name;
    }

    public void Name(String str)
    {
        _name = str;
    }

    public String Instruction()
    {
        return _instruction;
    }

    public void Instruction(String str)
    {
        _instruction = str;
    }

    public String Difficulty()
    {
        return _difficulty;
    }

    public void Difficulty(String str)
    {
        _difficulty = str;
    }

    public String Category() { return _category; }

    public void Category(String str) { _category = str; }

    public String Description()
    {
        return _description;
    }

    public void Description(String str)
    {
        _description = str;
    }

    public String Cooktime()
    {
        return _cooktime;
    }

    public void Cooktime(String str)
    {
        _cooktime = str;
    }

    public ArrayList<String> Ingredients()
    {
        return _ingredients;
    }

    public void Ingredients(ArrayList<String> list)
    {
        _ingredients = list;
    }
}
