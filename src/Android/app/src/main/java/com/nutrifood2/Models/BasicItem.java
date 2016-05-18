package com.nutrifood2.Models;

// Import needed to execute the code
import android.graphics.Bitmap;

/**
 * Model for the basic item
 *
 * @author DimitriAndMathias
 * @version 2016.0501
 * @since 2.0
 */
public class BasicItem
{
    // Private variables
    private String mName = null;
    private String mId = null;
    private String mContent = null;
    private int mResId = -1;
    private String mStrimage = null;
    private Bitmap mBitmap = null;
    private String mDate = null;

    /**
     * Public constructor
     *
     * @author DimitriAndMathias
     * @version 2016.0501
     */
    public BasicItem() {}

    /**
     * Public constructor that saves the given item's name
     *
     * @author DimitriAndMathias
     * @param name The given item's name
     * @version 2016.0501
     */
    public BasicItem(String name) {
        mName = name;
    }

    /**
     * Public constructor that saves the given item's name and its id
     *
     * @author DimitriAndMathias
     * @param name The given item's name
     * @param resId The given item's id
     * @version 2016.0501
     */
    public BasicItem(String name, int resId)
    {
        mName = name;
        mResId = resId;
    }

    /**
     * Public constructor that saves the given item's name and its content
     *
     * @author DimitriAndMathias
     * @param name The given item's name
     * @param content The given item's content
     * @version 2016.0501
     */
    public BasicItem(String name, String content)
    {
        mName = name;
        mContent = content;
    }

    /**
     * Public constructor that saves the given item's name, its id and its content
     *
     * @author DimitriAndMathias
     * @param name The given item's name
     * @param id The given item's id
     * @param content The given item's content
     * @version 2016.0501
     */
    public BasicItem(String name, String id, String content)
    {
        mName = name;
        mContent = content;
        mId = id;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param name The given item's name
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Name(String name) { mName = name; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The name
     */
    public String Name() { return mName; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param id The given item's id
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Id(String id) { mId = id; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The id
     */
    public String Id() { return mId; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param content The given item's content
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Content(String content) { mContent = content; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The content
     */
    public String Content() { return mContent; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The image's str
     */
    public String StrImage() { return mStrimage; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param str The given image's str
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void StrImage(String str) { mStrimage = str; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param bitmap The given item's bitmap
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Bitmap(Bitmap bitmap) { mBitmap = bitmap; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The bitmap
     */
    public Bitmap Bitmap() { return mBitmap; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param id The given image's id
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void ImageId(int id) { mResId = id; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The id
     */
    public int ImageId() { return mResId; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param str The given date's str
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Date(String str) { mDate = str; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The date
     */
    public String Date() { return mDate; }
}
