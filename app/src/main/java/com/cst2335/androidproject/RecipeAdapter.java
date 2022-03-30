package com.cst2335.androidproject;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cst2335.androidproject.R;

import java.util.List;

public class RecipeAdapter extends BaseAdapter {

    private List<RecipeNewData> data;
    private LayoutInflater inflater;
    public double calData;
    public double fatData;

    /**
     * constructor for instantiation
     *
     * @param context Context
     * @param data    List<NutritionNewBean>
     */
    public RecipeAdapter(Context context, List<RecipeNewData> data) {
        super();
        this.data = data;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.recipelistview, null);
            viewHolder.searchedFood = (TextView) convertView.findViewById(R.id.searchedFood);
            viewHolder.fat = (TextView) convertView
                    .findViewById(R.id.fat);
            viewHolder.calories = (TextView) convertView
                    .findViewById(R.id.cal);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        calData = data.get(position).getCalories();
        fatData = data.get(position).getFat();
        String calString = Double.toString(calData);
        String fatString = Double.toString(fatData);
        viewHolder.searchedFood.setText("Food: " + RecipeSearchMainActivity.food);
        viewHolder.fat.setText("Fat: " + fatString + " g ");
        viewHolder.calories.setText("Calories: " + calString + " g ");
        return convertView;
    }
    class ViewHolder {
        public TextView searchedFood, fat, calories;

    }
}