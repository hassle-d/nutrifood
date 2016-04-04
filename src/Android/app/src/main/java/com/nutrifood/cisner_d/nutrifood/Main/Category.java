package com.nutrifood.cisner_d.nutrifood.Main;

import android.graphics.Bitmap;

public class Category {
    private String _id = null;
    private String _name = null;
    private Bitmap _bitmap = null;

    public Category() {}
    public Category(String name, Bitmap bitmap)
    {
        _name = name;
        _bitmap = bitmap;
    }

    public void Name(String name) { _name = name; }
    public String Name() { return _name; }

    public void Id(String id) { _id = id; }
    public String Id() { return _id; }

    public Bitmap Bitmap() {
        return _bitmap;
    }

    public void Bitmap(Bitmap _bitmap) {
        this._bitmap = _bitmap;
    }
}
