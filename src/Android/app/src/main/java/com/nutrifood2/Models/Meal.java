package com.nutrifood2.Models;

import java.util.ArrayList;

public class Meal extends BasicItem {
    private String mAuthor;
    private String mDescription;
    private String mDifficulty;
    private String mCategory;
    private String mOldCategory;
    private String mCooktime;
    private int mRating;
    private ArrayList<String> mInstruction;
    private ArrayList<String> mIngredients;

    void Meal() {}

    public String Author()
    {
        return mAuthor;
    }

    public void Author(String str)
    {
        mAuthor = str;
    }

    public ArrayList<String> Instruction()
    {
        return mInstruction;
    }

    public void Instruction(ArrayList<String> list)
    {
        mInstruction = list;
    }

    public String Difficulty()
    {
        return mDifficulty;
    }

    public void Difficulty(String str)
    {
        mDifficulty = str;
    }

    public String Category() { return mCategory; }

    public void Category(String str) { mCategory = str; }

    public String OldCategory() { return mOldCategory; }

    public void OldCategory(String str) { mOldCategory = str; }

    public String Description()
    {
        return mDescription;
    }

    public void Description(String str)
    {
        mDescription = str;
    }

    public String Cooktime()
    {
        return mCooktime;
    }

    public void Cooktime(String str)
    {
        mCooktime = str;
    }

    public ArrayList<String> Ingredients()
    {
        return mIngredients;
    }

    public void Ingredients(ArrayList<String> list)
    {
        mIngredients = list;
    }

    public int Rating() { return mRating; }

    public void Rating(int rating) { mRating = rating; }
}
