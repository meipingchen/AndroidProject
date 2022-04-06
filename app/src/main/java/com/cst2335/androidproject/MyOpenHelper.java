package com.cst2335.androidproject;
/**
 * File name: MyOpenHelper.java
 * Author: Author: Qin Li / Jin Zhang / Meiping Chen
 * Course: CST2335 - Mobile Graphical Interface Prog.
 * Final project
 * Date: 2022-04-08
 * Lab Professor: Frank Emanuel
 * @author: Qin Li / Jin Zhang / Meiping Chen
 * Purpose: To set up the database
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This is the database class for storing the items that user saves.
 */
public class MyOpenHelper extends SQLiteOpenHelper {

    public static final String filename = "MyDatabase";
    public static final int version = 1;
    public static final String TABLE_NAME = "MyData";
    public static final String KEY_ID = "_id";
    public static final String COL_ingredient = "ingredient";
    public static final String COL_title = "title";
    public static final String COL_url = "url";
    public static final boolean COL_isFavorite = false;

    /**
     * the constructor for instantiation
     *
     * @param context Context
     */
    public MyOpenHelper(Context context) {
        super(context, filename, null, version);
    }


    /**
     * create the database with creation statement
     *
     * @param db SOLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table MyData ( _id INTEGER PRIMARY KEY AUTOINCREMENT, Message TEXT, SendOrReceive INTEGER, TimeSent TEXT );
        String result = String.format(" %s %s %s", "FirstString" , "10", "10.0" );

        //                                      //TABLE_NAME               take care of id numbers
        db.execSQL( String.format( "Create table %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s INTEGER);"
                , TABLE_NAME, KEY_ID,COL_ingredient, COL_title, COL_url,COL_isFavorite));

    }

    /**
     * upgrade the database by increasing the version number
     *
     * @param db         SQLiteDatabase
     * @param oldVersion the old database
     * @param newVersion the new database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "Drop table if exists " + TABLE_NAME ); //deletes the current data
        //create a new table:

        this.onCreate(db); //calls function on line 26
    }

}