package com.cst2335.androidproject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.cst2335.androidproject.RecipeDBHelper.RecipeArr;
import static com.cst2335.androidproject.RecipeDBHelper.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This activity is to store the searched food items.
 */
public class RecipeFavouriteList extends AppCompatActivity {
    private static final String TAG = "NutritionFavouriteList";
    private RecipeDBHelper foodDatabaseHelper;
    private ListView fListView;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<String> listData;
    private Cursor cursor;
    private ArrayAdapter adapter;
    public static String selectedName;

    /**
     * to create the favourite list activity
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition_activity_favourite_list);
        fListView = (ListView) findViewById(R.id.favListView);
        populateListView();
    }

    /**
     * to populate the list view of the favourite food list
     */
    public void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView ");
        foodDatabaseHelper = new RecipeDBHelper(this);
        Cursor data = foodDatabaseHelper.getData();
        listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0)); //add the items that store in database to the array adapter
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData); // show the items on the list view
        fListView.setAdapter(adapter);

        //click on an food item of fav list, shows the details fragment.
        fListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedName = adapterView.getItemAtPosition(position).toString(); //to retrieve the name of the food that has been entered.
                foodDatabaseHelper = new RecipeDBHelper(getApplicationContext());
                sqLiteDatabase = foodDatabaseHelper.getReadableDatabase();
                cursor = foodDatabaseHelper.getSpecificFood(selectedName, sqLiteDatabase);//to get the data from the database
                double cal = 0;
                double fat = 0;

                if (cursor.moveToFirst()) {
                    cal = cursor.getDouble(1); // to get the calories content based on the primary key which is the food that is shown on the fav list.
                    fat = cursor.getDouble(2); // to get the fat content based on the primary key which is the food that is shown on the fav list.
                    Log.d(TAG, "FAT " + fat + "Cal " + cal);
                }
                String calData = Double.toString(cal);
                String fatData = Double.toString(fat);

                Log.d(TAG, "onItemClick: You clicked on " + selectedName);

                //listview on tablet
                if (findViewById(R.id.frameLayout) != null) {
                    RecipeFragment nutritionFragment = new RecipeFragment();
                    Bundle bundle = new Bundle(); //save the data in the bundle for later retrieval.
                    bundle.putString("id", selectedName);
                    bundle.putString("calories", calData);
                    bundle.putString("fat", fatData);
                    bundle.putBoolean("isTablet", true);  //go to the fragment if it is a tablet.
                    nutritionFragment.setArguments(bundle);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frameLayout, nutritionFragment);
                    ft.addToBackStack("A string");
                    ft.commit();
                    Log.i(TAG, "Run on Tablet");

                } else {
                    //go to the new detailed activity if it is a phone.
                    Intent intent = new Intent(RecipeFavouriteList.this, RecipeDetailActivity.class);
                    intent.putExtra("id", selectedName);
                    intent.putExtra("calories", calData);
                    intent.putExtra("fat", fatData);
                    startActivityForResult(intent, 1);
                    Log.i(TAG, "Run on phone");
                }

            }
        });//end of click listener
    }//end of populate view

    /**
     * to show the toast message
     *
     * @param message the message to show
     */
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * to retrieve data of a specific entity from the database.
     */
    @SuppressLint("Range")
    public void query() {
        listData.clear();
        cursor = foodDatabaseHelper.getData();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Log.i(TAG, "SQL MESSAGE:" + cursor.getString((cursor.getColumnIndex(RecipeDBHelper.KEY_ID))));
                listData.add(cursor.getString(cursor.getColumnIndex(foodDatabaseHelper.KEY_ID)));
                cursor.moveToNext();
            }
            adapter.notifyDataSetChanged();
            Log.i(TAG, "Cursor's column count =  " + cursor.getColumnCount());
        }
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(TAG, "Cursor's column name is " + (i + 1) + ". " + cursor.getColumnName(i));
        }
    }

    /**
     * to notify any changes that on the list
     */
    @SuppressLint("Range")
    public void notifyChange() {
        fListView.setAdapter(adapter);
        sqLiteDatabase = foodDatabaseHelper.getWritableDatabase();

        cursor = sqLiteDatabase.query(false, TABLE_NAME, RecipeArr, null, null, null, null, null, null);
        int numColumns = cursor.getColumnCount();
        int numResult = cursor.getCount();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.i(TAG, "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(foodDatabaseHelper.KEY_ID)));
            listData.add(cursor.getString(cursor.getColumnIndex(foodDatabaseHelper.KEY_ID)));
            cursor.moveToNext();
        }
        Log.i(TAG, "Cursor's column count = " + numColumns);

        cursor.moveToFirst();
        for (int i = 0; i < numResult; i++) {
            Log.i(TAG, "The " + i + " row is " + cursor.getString(cursor.getColumnIndex(foodDatabaseHelper.KEY_ID)));
            cursor.moveToNext();
        }
    }

    /**
     * to refresh the activity when food is deleted.
     *
     * @param requestCode int
     * @param resultCode  int
     * @param data        Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            //sqLiteDatabase.delete(NutritionDatabaseHelper.TABLE_NAME, NutritionDatabaseHelper.KEY_ID + " = ?", new String[] {selectedName}) ;
            sqLiteDatabase = foodDatabaseHelper.getWritableDatabase();
            foodDatabaseHelper.delFood(selectedName, sqLiteDatabase);
            notifyChange();


        }
    }

    protected void onResume() {
        super.onResume();
        Log.i(TAG, "In onResume()");
    }

    protected void onStart() {
        super.onStart();
        foodDatabaseHelper.openDatabase();
        query();

        Log.i(TAG, "In onStart()");
    }

    protected void onPause() {
        super.onPause();
        Log.i(TAG, "In onPause()");
    }

    protected void onStop() {
        super.onStop();
        Log.i(TAG, "In onStop()");
    }

    protected void onDestroy() {
        super.onDestroy();
        foodDatabaseHelper.closeDatabase();
        Log.i(TAG, "In onDestroy()");
    }

}

