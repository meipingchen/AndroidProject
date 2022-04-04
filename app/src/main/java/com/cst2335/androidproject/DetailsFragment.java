package com.cst2335.androidproject;

import android.content.ContentValues;
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
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

public class DetailsFragment extends Fragment {
    private Bundle bundle;
    SQLiteDatabase theDatabase;
    //MyOpenHelper myOpener = new MyOpenHelper(DetailsFragment.this);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recipe_fragment,container, false);
        TextView ingredient = view.findViewById(R.id.ingredient2);
        TextView title = view.findViewById(R.id.title2);
        TextView url = view.findViewById(R.id.url2);
        bundle = getArguments();
        //fetch the message, id, and boolean info back from bundle
        String ingredient1 = bundle.getString("Ingredient");
        String title1 = bundle.getString("title");
        String url1 = bundle.getString("url");
        //locate message, id
        ingredient.setText("Ingredient: "+ingredient1);
        title.setText("Title: "+title1);
        url.setText("URL: "+url1);

        url.setOnClickListener(click->{
            Uri uri=Uri.parse(url1);
            Intent w = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(w);
        });
        Switch save = view.findViewById(R.id.switch1);
        String saveNow = "Save now";
        String notSave = "Not save";
        String undo = "undo";

        save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean b) {
                if (b == true) {
                    Snackbar.make(view, saveNow, Snackbar.LENGTH_LONG).setAction( undo, click -> cb.setChecked(!b)).show();

                } else if (b == false) {
                    Snackbar.make(view, notSave, Snackbar.LENGTH_LONG).setAction( undo, click -> cb.setChecked(!b)).show();
                }

            }
        });



        return view;
    }


/*
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox2:
                if (checked)
                //saveToDatabase();
        else
                break;
            // Perform your logic
        }
    }
    public void saveToDatabase(){
        //Log.d(TAG, "Writing to Database");
        //MyOpenHelper myOpener = new MyOpenHelper(this);
        //myOpener = new MyOpenHelper( this );
        theDatabase = myOpener.getWritableDatabase();
        ContentValues newRow = new ContentValues();
        newRow.put( MyOpenHelper.COL_ingredient , ingredientData );
        newRow.put(MyOpenHelper.COL_title, titleData);
        newRow.put(MyOpenHelper.COL_url,urlData);
        //now that columns are full, you insert:
        theDatabase.insert( MyOpenHelper.TABLE_NAME, null, newRow );
        //theDatabase.close();
    }
 */
}