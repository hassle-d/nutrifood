package com.nutrifood2.Models;

/**
 * Model for the comment
 *
 * @author DimitriAndMathias
 * @version 2016.0501
 * @since 2.0
 */
public class Comment extends BasicItem
{
    // Private variables
    private String mAuthor = null;
    private String mMealId = null;

    /**
     * Public constructor
     *
     * @author DimitriAndMathias
     * @version 2016.0501
     */
    void Comment() {}

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param str The given comment's author
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Author(String str) { mAuthor = str; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The author
     */
    public String Author() { return mAuthor; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param str The given comment's str
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void MealId(String str) { mMealId = str; }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The meal's id
     */
    public String MealId() { return mMealId; }
}
