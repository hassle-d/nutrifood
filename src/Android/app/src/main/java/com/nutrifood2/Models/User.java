package com.nutrifood2.Models;

// Import needed to execute the code
import java.util.ArrayList;

/**
 * Model for the user
 *
 * @author DimitriAndMathias
 * @version 2016.0501
 * @since 2.0
 */
public class User
{
    // Private variables
    private String mUsername;
    private String mPassword;
    private String mEmail;
    private String mFirstname;
    private String mLastname;
    private int mAge;
    private String mDescription;
    private ArrayList<String> mSpecial;
    private ArrayList<String> mAllergy;

    /**
     * Public constructor
     *
     * @author DimitriAndMathias
     * @version 2016.0501
     */
    public void User() {}

    /**
     * Public constructor that saves the given item's name and its id
     *
     * @author DimitriAndMathias
     * @param username The given user's username
     * @param password The given user's password
     * @param email The given user's email
     * @param firstname The given user's firstname
     * @param lastname The given user's ilastnamed
     * @param age The given user's iaged
     * @version 2016.0501
     */
    public void User(String username, String password, String email, String firstname, String lastname, int age)
    {
        mUsername = username;
        mPassword = password;
        mEmail = email;
        mFirstname = firstname;
        mLastname = lastname;
        mAge = age;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The user's username
     */
    public String Username()
    {
        return mUsername;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param str The given user's username
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Username(String str)
    {
        mUsername = str;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The user's password
     */
    public String Password()
    {
        return mPassword;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param str The given user's password
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Password(String str)
    {
        mPassword = str;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The user's firstname
     */
    public String Firstname()
    {
        return mFirstname;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param str The given user's firstname
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Firstname(String str)
    {
        mFirstname = str;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The user's lastname
     */
    public String Lastname()
    {
        return mLastname;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param str The given user's lastname
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Lastname(String str)
    {
        mLastname = str;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The user's email
     */
    public String Email()
    {
        return mEmail;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param str The given user's email
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Email(String str)
    {
        mEmail = str;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The user's age
     */
    public int Age()
    {
        return mAge;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param i The given user's age
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Age(int i)
    {
        mAge = i;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The user's description
     */
    public String Description()
    {
        return mDescription;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param str The given user's description
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
     * @return The user's spetial
     */
    public ArrayList<String> Spetial()
    {
        return mSpecial;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param list The given user's spetial
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Spetial(ArrayList<String> list)
    {
        mSpecial = list;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @version 2010.1105
     * @since 1.0
     * @return The user's alergies
     */
    public ArrayList<String> Allergy()
    {
        return mAllergy;
    }

    /**
     * This method simply .
     *
     * @author DimitriAndMathias
     * @param list The given user's alergies
     * @version 2010.1105
     * @since 1.0
     * @return nothing
     */
    public void Allergy(ArrayList<String> list)
    {
        mAllergy = list;
    }
}
