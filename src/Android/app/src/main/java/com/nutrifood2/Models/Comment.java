package com.nutrifood2.Models;

public class Comment extends BasicItem {
    private String mAuthor = null;
    private String mMealId = null;

    void Comment() {}

    public void Author(String str) { mAuthor = str; }
    public String Author() { return mAuthor; }

    public void MealId(String str) { mMealId = str; }
    public String MealId() { return mMealId; }
}
