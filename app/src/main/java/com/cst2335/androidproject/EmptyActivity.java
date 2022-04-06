package com.cst2335.androidproject;

/**
 * File name: EmptyActivity.java
 * Author: Author: Qin Li / Jin Zhang / Meiping Chen
 * Course: CST2335 - Mobile Graphical Interface Prog.
 * Final project
 * Date: 2022-04-08
 * Lab Professor: Frank Emanuel
 * @author: Qin Li / Jin Zhang / Meiping Chen
 * Purpose: connect activity and fragment
 */
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
public class EmptyActivity extends AppCompatActivity {

    /**
     * to create the empty activity
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_activity);
        //get bundle from previous activity
        Bundle bundle=getIntent().getBundleExtra("FavouriteRecipe");
        DetailsFragment fragment=new DetailsFragment();
        fragment.setArguments(bundle);
        //go to fragment
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.setReorderingAllowed(true);
        ft.replace(R.id.frameLayout,fragment);
        ft.commit();
    }
}