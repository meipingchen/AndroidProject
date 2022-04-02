package com.cst2335.androidproject;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.round;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * to create the nutrition detail activity
 */
public class RecipeFragment extends Fragment {

    private View view;
    private TextView caloriesTextView;
    private TextView fatTextView;
    private Button delBtn;
    private RecipeDBHelper foodDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Button nutritionTagBtn;
    private String foodTag;
    private EditText tagEditTxt;
    private String primaryFoodKey;
    private TextView foodTagTextView;
    private Cursor cursor;
    private String tag;
    private Button statBtn;
    private ArrayList<Double> calStat = new ArrayList<>();
    private double calTotal;
    private double calAve;
    private double calMax;
    private double calMin;
    private static final String TAG = "NutritionFragment";
    private Button tagDelBtn;
    private TextView foodNameTextView;


    public RecipeFragment() {
    }

    /**
     * to create fragment view of nutrition details
     *
     * @param inflater           Inflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recipe_activity_fragment, container, false);
        return view;
    }

    /**
     * to create the activity of detail fragment
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        caloriesTextView = view.findViewById(R.id.calories_frag);
        fatTextView = view.findViewById(R.id.fat_frag);
        delBtn = view.findViewById(R.id.detail_delete_button);
        nutritionTagBtn = view.findViewById(R.id.nutritionTag);
        tagEditTxt = view.findViewById(R.id.tagEditTxt);
        foodTagTextView = view.findViewById(R.id.food_tag);
        tagDelBtn = view.findViewById(R.id.tagDelBtn);
        foodNameTextView = view.findViewById(R.id.foodName);
        statBtn = view.findViewById(R.id.statTag);

        primaryFoodKey = RecipeFavouriteList.selectedName;
        foodDatabaseHelper = new RecipeDBHelper(getActivity());
        sqLiteDatabase = foodDatabaseHelper.getReadableDatabase();
        cursor = foodDatabaseHelper.getSpecificFood(primaryFoodKey, sqLiteDatabase);//to get the data from the database
        if (cursor.moveToFirst()) {
            tag = cursor.getString(3);
            Log.d(TAG, " Tag " + tag);
            foodTagTextView.setText(tag);
        }

        caloriesTextView.setText(getArguments().getString("calories"));
        fatTextView.setText(getArguments().getString("fat"));
        foodNameTextView.setText(primaryFoodKey);

        //to delete the food item
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setMessage(getString(R.string.delConfMsg) + primaryFoodKey + " ?").setCancelable(false).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getArguments().getBoolean("isTablet")) {
                            sqLiteDatabase = foodDatabaseHelper.getWritableDatabase();
                            foodDatabaseHelper.delFood(getArguments().getString("id"), sqLiteDatabase);
                            ((RecipeFavouriteList) getActivity()).notifyChange();
                            ((RecipeFavouriteList) getActivity()).query();
                            getFragmentManager().beginTransaction().remove(RecipeFragment.this).commit();
                        } else {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("id", getArguments().getString("id"));
                            getActivity().setResult(1, resultIntent);
                            getActivity().finish();

                        }

                    }
                }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = mBuilder.create();
                alert.setTitle(getString(R.string.alertTitle));
                alert.show();

            }
        });
        //to add a tag to a food
        nutritionTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodTag = tagEditTxt.getText().toString();
                if (tag == null) {
                    if (foodTag != null && !(foodTag.isEmpty())) {
                        UpdateData(foodTag, primaryFoodKey);
                        foodTagTextView.setText(foodTag);
                        tagEditTxt.setText("");
                    } else {
                        toastMessage(getString(R.string.prompt_to_enter));
                    }
                } else {
                    toastMessage(getString(R.string.tag_already));
                }

            }


        });

        //to get the statistics of total, average, min and max on a specific tag
        statBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor = foodDatabaseHelper.getSpecificFood(primaryFoodKey, sqLiteDatabase);//to get the data from the database
                if (cursor.moveToFirst()) {
                    tag = cursor.getString(3);
                    if (tag != null) {
                        cursor = foodDatabaseHelper.getTag(tag, sqLiteDatabase);
                        if (cursor.moveToFirst()) {
                            do {
                                calStat.add(cursor.getDouble(1));
                            } while (cursor.moveToNext());
                        }
                        calMax = Collections.max(calStat); //get max of calories
                        calMin = Collections.min(calStat); //get min of calories
                        calTotal = foodDatabaseHelper.getSum(tag); //get total calories
                        calAve = round(calTotal / calStat.size()); //get average calories

                        //alertdialog box to show the stattics of max, min, total and average of calories under the same tag name
                        AlertDialog calStatDialog = new AlertDialog.Builder(getActivity())
                                .setTitle(getString(R.string.tag_stat_title) + tag)
                                .setMessage(getString(R.string.max_cal) + Double.toString(calMax) + " g" + "\n"
                                        + getString(R.string.min_cal) + Double.toString(calMin) + " g" + "\n" +
                                        getString(R.string.total_cal) + calTotal + " g" + "\n" +
                                        getString(R.string.ave_cal) + calAve + " g")
                                .setCancelable(true)
                                .create();
                        calStatDialog.show();


                    } else {
                        toastMessage(getString(R.string.no_tag_found));
                    }
                }

            }
        });

        /**
         * to delete the food tag
         */
        tagDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodTagString = foodTagTextView.getText().toString();
                if (!(foodTagString.isEmpty())) {
                    DeleteTag(primaryFoodKey);
                } else {

                    toastMessage(getString(R.string.no_tag_found));
                }
                //getFragmentManager().beginTransaction().detach(NutritionFragment.this).attach(NutritionFragment.this).commit();
                //Log.i("IsRefresh", "Yes");

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(RecipeFragment.this).attach(RecipeFragment.this).commit();
                // FragmentTransaction fragTransaction =   (getActivity()).getFragmentManager().beginTransaction();
                //fragTransaction.detach(NutritionFragment.this);
                //fragTransaction.attach(NutritionFragment.this);
                //fragTransaction.commit();
            }
        });

    }

    /**
     * to delete the tag
     *
     * @param id primary key
     */
    public void DeleteTag(String id) {
        // foodDatabaseHelper = new NutritionDatabaseHelper(getActivity());
        boolean deleteTag = foodDatabaseHelper.deleteTag(id);
        if (deleteTag) {
            toastMessage(getString(R.string.tag_delete));
        } else {
            toastMessage(getString(R.string.error));
        }
    }

    /**
     * to update the database
     * @param food
     * @param id
     */
    public void UpdateData(String food, String id) {
        foodDatabaseHelper = new RecipeDBHelper(getActivity());
        boolean updateData = foodDatabaseHelper.updateName(food, id);
        if (updateData) {
            toastMessage(getString(R.string.tag_update));
        } else {
            toastMessage(getString(R.string.error));
        }
    }

    //toast message
    private void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}