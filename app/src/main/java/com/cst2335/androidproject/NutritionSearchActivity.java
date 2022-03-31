package com.cst2335.androidproject;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


/**
 * This is the activity for searching food.
 */
public class NutritionSearchActivity extends AppCompatActivity {
    private ProgressDialog loading = null;
    private NutritionJsonAdapter adapter;
    private EditText searchEditText;
    private Button btnAdd;
    private Button btnFavourite;
    private Button btnSearch;
    private ListView listView;
    protected static final String ACTIVITY_NAME = "NutritionSearchActivity";
    private String app_id = "40cb1f76", app_key = "9dd571cf4d9e83a7796c460130be79dd";
    private List<NutritionNewBean> newBeanList = new ArrayList<>();
    public static String food;
    private String jsonUrl = " https://api.edamam.com/api/food-database/parser?ingr=" + food + "&app_id=" + app_id + "&app_key=" + app_key;
    private NutritionDatabaseHelper foodDatabaseHelper = new NutritionDatabaseHelper(this);

    /**
     * to create the search activity
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition_activity_search);
        listView = (ListView) findViewById(R.id.searchResult);
        searchEditText = (EditText) findViewById(R.id.searchEditTxt);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnFavourite = (Button) findViewById(R.id.btn_favourite);
        Toolbar nutritionToolbar = (Toolbar) findViewById(R.id.nutrition_toolbar);
        setSupportActionBar(nutritionToolbar);

        //search button
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food = searchEditText.getText().toString();
                if (food != null && !food.isEmpty()) {
                    new MyAsyncTask().execute(jsonUrl);
                } else {
                    toastMessage(getString(R.string.prompt_to_enter));
                }
            }
        });

        //to add the food to the favourite list.
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food = searchEditText.getText().toString();

                if (food != null && !(food.isEmpty())) {
                    if (adapter != null) {
                        double fat = adapter.fatData;
                        double cal = adapter.calData;
                        AddData(food, cal, fat);
                    } else {
                        toastMessage(getString(R.string.no_result_found));
                    }
                } else {
                    Snackbar.make(v, getString(R.string.prompt_to_enter), Snackbar.LENGTH_LONG).setAction(
                            "Action", null
                    ).show();
                }

            }
        });

        //to go to favourite list.
        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutritionSearchActivity.this, NutritionFavouriteList.class);
                startActivity(intent);
            }
        });
    }

    /**
     * to add the data to database .
     *
     * @param food primary key in the database
     * @param cal  the detail that needs to be inserted in the calory column
     * @param fat  the detail that needs to be insterted in the fat column
     */
    public void AddData(String food, double cal, double fat) {
        boolean insertData = foodDatabaseHelper.addData(food, cal, fat);
        if (insertData) {
            toastMessage(getString(R.string.data_insert));
        } else {
            toastMessage(getString(R.string.error));
        }
    }

    /**
     * inner class to parse the API KEY
     */
    class MyAsyncTask extends AsyncTask<String, Void, List<NutritionNewBean>> {
        private String jsonUrl = " https://api.edamam.com/api/food-database/parser?ingr=" + food + "&app_id=" + app_id + "&app_key=" + app_key;
        NutritionJsonData jsonData = new NutritionJsonData();

        /**
         * the get the data from the Json Object
         *
         * @param params String
         * @return the data of the Json Object
         */
        @Override
        protected List<NutritionNewBean> doInBackground(String... params) {
            return newBeanList = jsonData.getJsonData(jsonUrl);
        }

        /**
         * the show the progress bar
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new ProgressDialog(NutritionSearchActivity.this);
            loading.setMessage(getString(R.string.waitmsg));
            loading.setCancelable(false);
            loading.show();
            ;
        }

        /**
         * to set up the listview of the searched result
         *
         * @param result the data from the Json Object
         */
        @Override
        protected void onPostExecute(List<NutritionNewBean> result) {
            super.onPostExecute(result);
            adapter = new NutritionJsonAdapter(NutritionSearchActivity.this, newBeanList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            if (loading.isShowing()) {
                loading.dismiss();
            }
        }

        /**
         * @param values
         */
        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * toolbar menu items
     *
     * @param menu Menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * multiple menu items for switching
     *
     * @param item Menuitem
     * @return booolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.help:
                AlertDialog alertDialog = new AlertDialog.Builder(NutritionSearchActivity.this).create();
                alertDialog.setTitle(getString(R.string.dialogboxTitle));
                alertDialog.setMessage(getString(R.string.author) + "\n" +
                        getString(R.string.version) + "\n" + getString(R.string.instruction));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
