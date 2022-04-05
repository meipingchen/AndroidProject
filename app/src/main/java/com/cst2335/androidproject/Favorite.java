package com.cst2335.androidproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Favorite extends AppCompatActivity {

    SQLiteDatabase theDatabase;
    MyOpenHelper myOpener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_list);

        myOpener = new MyOpenHelper(this);
        theDatabase = myOpener.getWritableDatabase();
        ArrayList<MainActivity.Recipe> favArray = new ArrayList<>();

        Cursor result = theDatabase.rawQuery("Select * from "
                + MyOpenHelper.TABLE_NAME + ";", null);
        int ingredient = result.getColumnIndex(MyOpenHelper.COL_ingredient);
        int title = result.getColumnIndex(MyOpenHelper.COL_title);
        int url = result.getColumnIndex(MyOpenHelper.COL_url);
        while (result.moveToNext()) {
            String ingredientString = result.getString(ingredient);
            String titleString = result.getString(title);
            String urlString = result.getString(url);

            favArray.add(new MainActivity.Recipe(ingredientString,titleString,urlString));

        }


        MainActivity.RecipeJsonAdapter favList = new MainActivity.RecipeJsonAdapter(this,favArray);
        ListView favListView = findViewById(R.id.favPage);
        favListView.setAdapter(favList);

        favListView.setOnItemLongClickListener( (p, b, pos, id) -> {
            MainActivity.Recipe whatWasClicked = favArray.get(pos);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")

                    //.setIcon(0)

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

                    //An optional third button:
                    //.setNeutralButton("Maybe", (click, arg) -> {  })


                    //You can add extra layout elements:
                    //.setView(getLayoutInflater().inflate(R.layout.row_layout_receive, null) )

                    //Show the dialog
                    .create().show();

            return true;
        });

        //printCursor(results,theDatabase.getVersion());

    }


}


