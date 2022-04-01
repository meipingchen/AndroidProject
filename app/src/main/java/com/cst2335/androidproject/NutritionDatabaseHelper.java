package com.cst2335.androidproject;

/**
 * File name: NutritionDatabaseHelper.java
 * NutritionAuthor: Feng Cheng, ID#:040719618
 * Course: CST2335 - Mobile Graphical Interface Prog.
 * Final project
 * Date: 2018-11-12
 * Professor: Eric
 * Purpose: To set up the database
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This is the database class for storing the items that user saves.
 */
public class NutritionDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FoodNutrition.db";
    public static final int VERSION_NUM = 6;
    public static final String KEY_ID = "food";
    public static final String COL2 = "Calory";
    public static final String COL3 = "Fat";
    public static final String COL4 = "Tag";
    public static final String TABLE_NAME = "Nutrition_Table";
    public static final String TAG = "NutritionDatabaseHelper";
    private SQLiteDatabase database;
    public static String[] NUTRITION_FIELDS = new String[]{
            KEY_ID, COL2, COL3
    };

    /**
     * the constructor for instantiation
     *
     * @param cxt Context
     */
    public NutritionDatabaseHelper(Context cxt) {
        super(cxt, DATABASE_NAME, null, VERSION_NUM);
    }

    /**
     * to create the database
     *
     * @param db SOLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " TEXT PRIMARY KEY, " + COL2 + " REAL," + COL3 + " REAL," +
                COL4 + " TEXT);");


        Log.i(TAG, "Calling onCreate()");
    }

    /**
     * to upgrade the database by increasing the version number,
     *
     * @param db         SQLiteDatabase
     * @param oldVersion the old database
     * @param newVersion the new database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i(TAG, "Calling onUpdate(), oldVersion=" + oldVersion + ", newVersion=" + newVersion);
    }

    /**
     * to add data
     *
     * @param recipe primary id
     * @param title  the attribute
     * @param url  the attribute
     * @return the data inserted successfully or not
     */
    public boolean addData(String recipe, String title, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, recipe);
        contentValues.put(COL2, title);
        contentValues.put(COL3, url);

        Log.d(TAG, "addData: Adding " + recipe + title + url + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * to retrieve data from the database
     *
     * @return Cursor all data in the database.
     */
    public Cursor getData() {
        database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    /**
     * to retrieve a specific row from the database
     *
     * @param id             primary key
     * @param sqLiteDatabase SQLiteDatabase
     * @return Cursor the specific the row
     */
    public Cursor getSpecificFood(String id, SQLiteDatabase sqLiteDatabase) {
        String[] projections = {KEY_ID, COL2, COL3, COL4};
        String selections = KEY_ID + " LIKE ?";
        String[] selection_args = {id};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, projections, selections, selection_args, null, null, null);
        return cursor;
    }

    /**
     * to get the rows with the same tag.
     *
     * @param tag            a specific tag
     * @param sqLiteDatabase SQListeDatabase
     * @return the rows of the same tag
     */
    public Cursor getTag(String tag, SQLiteDatabase sqLiteDatabase) {
        String[] projections = {KEY_ID, COL2, COL3, COL4};
        String selections = COL4 + " LIKE ?";
        String[] selection_args = {tag};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, projections, selections, selection_args, null, null, null);
        return cursor;
    }

    /**
     * to update the tag column in the database after the users add a string tag to a food.
     *
     * @param tagName the tag that the uses enters.
     * @param id      the primary key of the database
     * @return the boolean type whether or not the tag is inserted into the column.
     */
    public boolean updateName(String tagName, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL4, tagName);
        long result = db.update(TABLE_NAME, contentValues, "food = ?", new String[]{id});
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * to delete food tag
     *
     * @param id primary key
     * @return boolean if the tag is deleted or not
     */
    public boolean deleteTag(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String tag = null;
        contentValues.put(COL4, tag);
        long result = db.update(TABLE_NAME, contentValues, "food = ?", new String[]{id});
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * to delete a specific row from the database
     *
     * @param id             primary key
     * @param sqLiteDatabase SQLiteDatabase
     */
    public void delFood(String id, SQLiteDatabase sqLiteDatabase) {
        database = this.getWritableDatabase();
        String selection = KEY_ID + " LIKE ?";
        String[] selection_args = {id};
        sqLiteDatabase.delete(TABLE_NAME, selection, selection_args);
    }

    /**
     * to get the total calories of the same tag.
     *
     * @param tag tag name
     * @return double calories
     */
    public double getSum(String tag) {
        String sql = "SELECT SUM(" + COL2 + ") FROM " + TABLE_NAME + " WHERE " + COL4 + " = " + "'" + tag + "'";
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        double sumCal = cursor.getDouble(0);
        return sumCal;
    }

    /**
     * to open the writable database
     */
    public void openDatabase() {
        database = this.getWritableDatabase();
    }

    /**
     * to close the database
     */
    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

}