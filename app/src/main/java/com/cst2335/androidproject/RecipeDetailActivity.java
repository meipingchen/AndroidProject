package com.cst2335.androidproject;

import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class is the activity for nutrition details.
 */
public class NutritionDetailActivity extends AppCompatActivity {
    /**
     * to create the nutrition activity.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition_activity_detail_frag);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
        }

        Bundle bundle = new Bundle();
        bundle.putString("calories", getIntent().getStringExtra("calories"));
        bundle.putString("fat", getIntent().getStringExtra("fat"));

        NutritionFragment fragment = new NutritionFragment();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.detail_frameLayout, fragment);
        ft.commit();
    }
}