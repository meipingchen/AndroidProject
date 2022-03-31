package com.cst2335.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * the adapter to set up the view.
 */
public class NutritionJsonAdapter extends BaseAdapter {

    private List<NutritionNewBean> data;
    private LayoutInflater inflater;
    public double calData;
    public double fatData;

    /**
     * constructor for instantiation
     *
     * @param context Context
     * @param data    List<NutritionNewBean>
     */
    public NutritionJsonAdapter(Context context, List<NutritionNewBean> data) {
        super();
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    /**
     * to get the size of the List
     *
     * @return size of the List
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * to get the item of a specific position
     *
     * @param position int
     * @return the specific item
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    /**
     * to get the ID of a specific item
     *
     * @param position int
     * @return the position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * to set up the view
     *
     * @param position    int
     * @param convertView View
     * @param parent      ViewGroup
     * @return the convertView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.nutrition_activity_listview_item, null);
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
        viewHolder.searchedFood.setText("Food: " + NutritionSearchActivity.food);
        viewHolder.fat.setText("Fat: " + fatString + " g ");
        viewHolder.calories.setText("Calories: " + calString + " g ");
        return convertView;
    }

    /**
     * inner class to hold the view of detailed searched result
     */
    class ViewHolder {
        public TextView searchedFood, fat, calories;

    }
}