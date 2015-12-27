package com.nutrifood.cisner_d.nutrifood;

import android.content.Context;
import android.graphics.Bitmap;

import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Meal {
    private String  _author;
    private String  _name;
    private String  _description;
    private String  _instruction;
    private String  _difficulty;
    private String  _category;
    private String  _cooktime;
    private String  _id;
    private File    _image;
    private Bitmap _bitmap;
    private ArrayList<String> _ingredients;

    void Meal() {}

    public String Id() { return _id; }

    public void Id(String str) { _id = str; }

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

    public File Image() { return _image; }

    public void Image(String str, Context context)
    {
        Client.get(context.getString(R.string.image_URL) + "/" + str, null, new FileAsyncHttpResponseHandler(context) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, File response) {
                _image = response;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
            }
        });
    }

    public Bitmap Bitmap() {
        return _bitmap;
    }

    public void Bitmap(Bitmap _bitmap) {
        this._bitmap = _bitmap;
    }
}
