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
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

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
        url.setPaintFlags(url.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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

        //for toolbar
        Toolbar detailToolbar = view.findViewById(R.id.detail_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(detailToolbar);
        return view;
    }

    /**
     * get the view of toolbar
     *
     * @param menu Menu
     * @return boolean
     */
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
                Intent goToProfile = new Intent(getActivity().getApplication(), MainActivity.class);
                startActivity(goToProfile);
                return true;
            case R.id.help:
                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getActivity()).create();
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