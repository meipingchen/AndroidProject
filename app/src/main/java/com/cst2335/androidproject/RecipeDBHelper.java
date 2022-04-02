package com.cst2335.androidproject;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This is the database class for storing the items that user saves.
 */
public class RecipeDBHelper extends SQLiteOpenHelper {
    public static final String filename = "Recipe";
    public static final int version= 1;
    public static final String TABLE_NAME = "RecipeTable";
    public static final String KEY_ID = "_id";
    public static final String COL_ingredient = "ingredient";
    public static final String COL_title = "title";
    public static final String COL_url = "url";
    public static final String COL4 = "Tag";
    public static final String TAG = "RecipeDatabaseHelper";
    private SQLiteDatabase database;
    public static String[] RecipeArr = new String[]{
            KEY_ID, COL_ingredient, COL_title,COL_url
    };

    /**
     * the constructor for instantiation
     *
     * @param cxt Context
     */
    public RecipeDBHelper(Context cxt) {
        super(cxt, filename, null, version);
    }

    /**
     * to create the database
     *
     * @param db SOLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( String.format( "Create table %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s  INTEGER );"
                , TABLE_NAME, COL_ingredient, COL_title, COL_url));


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
     * @param ingredient primary id
     * @param title  the attribute
     * @param url  the attribute
     * @return the data inserted successfully or not
     */
    public boolean addData(String ingredient, String title, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newRow = new ContentValues();
        newRow.put(COL_ingredient, ingredient);
        newRow.put(COL_title, title);
        newRow.put(COL_url, url);

        Log.d(TAG, "addData: Adding " + ingredient + title + url + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, newRow);

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
        String[] projections = {KEY_ID, COL_ingredient, COL_title, COL_url};
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
        String[] projections = {KEY_ID, COL_ingredient, COL_title, COL_url};
        String selections = COL_url + " LIKE ?";
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
        contentValues.put(COL_url, tagName);
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
        String sql = "SELECT SUM(" + COL_ingredient + ") FROM " + TABLE_NAME + " WHERE " + COL4 + " = " + "'" + tag + "'";
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