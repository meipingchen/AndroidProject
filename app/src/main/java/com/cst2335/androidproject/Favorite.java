package com.cst2335.androidproject;

/**
 * File name: DetailsFragment.java
 * Author: Author: Qin Li / Jin Zhang / Meiping Chen
 * Course: CST2335 - Mobile Graphical Interface Prog.
 * Final project
 * Date: 2022-04-08
 * Lab Professor: Frank Emanuel
 * @author: Qin Li / Jin Zhang / Meiping Chen
 * Purpose: To get favorate recipes data from database
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class Favorite extends AppCompatActivity {

    SQLiteDatabase theDatabase;
    MyOpenHelper myOpener;

    /**
     * to create the Favorite activity
     * get data from Recipe database
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_list);

        myOpener = new MyOpenHelper(this);
        theDatabase = myOpener.getWritableDatabase();
        ArrayList<MainActivity.Recipe> favArray = new ArrayList<>();

        Cursor result = theDatabase.rawQuery("Select * from "
                + MyOpenHelper.TABLE_NAME + ";", null);
        int title = result.getColumnIndex(MyOpenHelper.COL_title);
        int ingredient = result.getColumnIndex(MyOpenHelper.COL_ingredient);
        int url = result.getColumnIndex(MyOpenHelper.COL_url);
        while (result.moveToNext()) {
            String titleString = result.getString(title);
            String ingredientString = result.getString(ingredient);
            String urlString = result.getString(url);

            favArray.add(new MainActivity.Recipe(titleString,ingredientString,urlString));
        }

        MainActivity.RecipeJsonAdapter favList = new MainActivity.RecipeJsonAdapter(this,favArray);
        ListView favListView = findViewById(R.id.favPage);
        favListView.setAdapter(favList);
        /**
         * long click the like button to delete the recipe
         */
        favListView.setOnItemLongClickListener( (p, b, pos, id) -> {
            MainActivity.Recipe whatWasClicked = favArray.get(pos);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")
                    //What is the message:
                    .setMessage("The selected row is:"+ pos+"\n"+"The database id is:"+id)
                    //what the Yes button does:
                    .setPositiveButton("Yes", (click, arg) -> {
                        favArray.remove(pos);
                        favList.notifyDataSetChanged();
                        theDatabase.delete(MyOpenHelper.TABLE_NAME,MyOpenHelper.COL_url+"=?",new String[]{whatWasClicked.getUrl()});
                    })
                    //What the No button does:
                    .setNegativeButton("No", (click, arg) -> { })

                    //Show the dialog
                    .create().show();

            return true;
        });

        //for toolbar
        Toolbar detailToolbar =  findViewById(R.id.detail_toolbar);
        detailToolbar.setTitle("Favourite List");
        setSupportActionBar(detailToolbar);
    }

    /**
     * get the view of toolbar
     *
     * @param menu Menu
     * @return boolean
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    /**
     * toolbar multiple menu items for switching
     *
     * @param item Menuitem
     * @return booolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                Intent goToProfile = new Intent(Favorite.this, MainActivity.class);
                startActivity(goToProfile);
                return true;
            case R.id.help:
                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Favorite.this).create();
                alertDialog.setTitle(getString(R.string.dialogboxTitle));
                alertDialog.setMessage(getString(R.string.author_full) + "\n" +
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
