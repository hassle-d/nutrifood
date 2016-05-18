package com.nutrifood2.Models;

import android.graphics.Bitmap;

public class BasicItem {
    private String mName = null;
    private String mId = null;
    private String mContent = null;
    private int mResId = -1;
    private String mStrimage = null;
    private Bitmap mBitmap = null;
    private String mDate = null;

    public BasicItem() {}

    public BasicItem(String name) {
        mName = name;
    }

    public BasicItem(String name, int resId)
    {
        mName = name;
        mResId = resId;
    }

    public BasicItem(String name, String content) {
        mName = name;
        mContent = content;
    }

    public BasicItem(String name, String id, String content) {
        mName = name;
        mContent = content;
        mId = id;
    }

    public void Name(String name) { mName = name; }

    public String Name() { return mName; }

    public void Id(String id) { mId = id; }

    public String Id() { return mId; }

    public void Content(String content) { mContent = content; }

    public String Content() { return mContent; }

    public String StrImage() { return mStrimage; }

    public void StrImage(String str) { mStrimage = str; }

    public void Bitmap(Bitmap bitmap) { mBitmap = bitmap; }

    public Bitmap Bitmap() { return mBitmap; }

    public void ImageId(int id) { mResId = id; }

    public int ImageId() { return mResId; }

    public void Date(String str) { mDate = str; }

    public String Date() { return mDate; }
}
