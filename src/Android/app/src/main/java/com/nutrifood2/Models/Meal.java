package com.nutrifood2.Models;

// Import needed to execute the code
import java.util.ArrayList;

/**
 * Model for the basic item
 *
 * @author DimitriAndMathias
 * @version 2016.0501
 * @since 2.0
 */
public class Meal extends BasicItem
{
    // Private variables
    private String mAuthor;
    private String mDescription;
    private String mDifficulty;
    private String mCategory;
    private String mOldCategory;
    private String mCooktime;
    private double mRating;
    private ArrayList<String> mInstruction;
    private ArrayList<String> mIngredients;

    /**
     * Public constructor
     *
     * @author DimitriAndMathias
     * @version 2016.0501
     */
    void Meal() {}

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The author
     */
    public String Author()
    {
        return mAuthor;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param str The given item's author
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Author(String str)
    {
        mAuthor = str;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The item's difficulty
     */
    public String Difficulty()
    {
        return mDifficulty;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param str The given item's difficulty
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Difficulty(String str)
    {
        mDifficulty = str;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The item's category
     */
    public String Category() { return mCategory; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param str The given item's category
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Category(String str) { mCategory = str; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The item's old category
     */
    public String OldCategory() { return mOldCategory; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param str The given item's old category
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void OldCategory(String str) { mOldCategory = str; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The item's description
     */
    public String Description()
    {
        return mDescription;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param str The given item's description
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Description(String str)
    {
        mDescription = str;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The item's cooking time
     */
    public String Cooktime()
    {
        return mCooktime;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param str The given item's cooking time
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Cooktime(String str)
    {
        mCooktime = str;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The item's ingredients
     */
    public ArrayList<String> Ingredients()
    {
        return mIngredients;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param list The given item's ingredients
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Ingredients(ArrayList<String> list)
    {
        mIngredients = list;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The item's instructions
     */
    public ArrayList<String> Instruction()
    {
        return mInstruction;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param list The given item's instructions
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Instruction(ArrayList<String> list)
    {
        mInstruction = list;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The rating
     */
    public double Rating() { return mRating; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param rating The given item's rating
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Rating(double rating) { mRating = rating; }
}
