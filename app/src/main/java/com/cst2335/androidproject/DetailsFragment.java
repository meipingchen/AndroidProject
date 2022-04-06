package com.cst2335.androidproject;

/**
 * File name: DetailsFragment.java
 * Author: Author: Qin Li / Jin Zhang / Meiping Chen
 * Course: CST2335 - Mobile Graphical Interface Prog.
 * Final project
 * Date: 2022-04-08
 * Lab Professor: Frank Emanuel
 * @author: Qin Li / Jin Zhang / Meiping Chen
 * Purpose: To display recipe details, including title, ingredients and url
 */
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

/**
 * to create the recipe details activity
 */
public class DetailsFragment extends Fragment {
    private Bundle bundle;
    SQLiteDatabase theDatabase;
    MyOpenHelper myOpener;
    String ingredient1;
    String title1;
    String url1;

    /**
     * to create fragment view of recipe details
     *
     * @param inflater           Inflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recipe_fragment,container, false);
        TextView ingredient = view.findViewById(R.id.ingredient2);
        TextView title = view.findViewById(R.id.title2);
        TextView url = view.findViewById(R.id.url2);
        bundle = getArguments();
        //fetch the ingredient,title, url info back from bundle
        ingredient1 = bundle.getString("Ingredient");
        title1 = bundle.getString("title");
        url1 = bundle.getString("url");
        //locate ingredient, title, url
        ingredient.setText("Ingredient: "+ingredient1);
        title.setText("Title: "+title1);
        url.setText("URL: "+url1);
        /**
         * click the URL link to go to URL website page
         */
        url.setOnClickListener(click->{
            Uri uri=Uri.parse(url1);
            Intent w = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(w);
        });
        Switch save = view.findViewById(R.id.switch1);
        String saveNow = "Save now";
        String notSave = "Not save";
        String undo = "undo";
        /**
         * click the save button to save the favourite recipe
         */
        save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean b) {
                if (b == true) {
                    Snackbar.make(view, saveNow, Snackbar.LENGTH_LONG).setAction( undo, click -> cb.setChecked(!b)).show();
                    saveToDatabase();
                } else if (b == false) {
                    Snackbar.make(view, notSave, Snackbar.LENGTH_LONG).setAction( undo, click -> cb.setChecked(!b)).show();
                }
            }
        });

        return view;
    }

    /**
     * to create a database opener
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myOpener = new MyOpenHelper(context);
    }

    /**
     * to close the database
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        theDatabase.close();
    }

    /**
     * to save data to the database
     */
    public void saveToDatabase(){

        theDatabase = myOpener.getWritableDatabase();
        ContentValues newRow = new ContentValues();
        newRow.put( MyOpenHelper.COL_ingredient , ingredient1 );
        newRow.put(MyOpenHelper.COL_title, title1);
        newRow.put(MyOpenHelper.COL_url,url1);
        //now that columns are full, you insert:
        theDatabase.insert( MyOpenHelper.TABLE_NAME, null, newRow );

    }

}