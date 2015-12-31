package com.nutrifood.cisner_d.nutrifood.Main;

public class Category {
    private String  _id = null;
    private String  _name = null;

    public void Name(String name) { _name = name; }
    public String Name() { return _name; }

    public void Id(String id) { _id = id; }
    public String Id() { return _id; }
}
