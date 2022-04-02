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
public class RecipeJsonAdapter extends BaseAdapter {

    private List<RecipeNewBean> data;
    private LayoutInflater inflater;
    public String titleData;
    public String urlData;
    public String ingredientData;

    /**
     * constructor for instantiation
     *
     * @param context Context
     * @param data    List<RecipeNewBean>
     */
    public RecipeJsonAdapter(Context context, List<RecipeNewBean> data) {
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
            convertView = inflater.inflate(R.layout.recipe_activity_listview_item, null);
            viewHolder.searchedFood = (TextView) convertView.findViewById(R.id.searchedFood);
            viewHolder.fat = (TextView) convertView
                    .findViewById(R.id.fat);
            viewHolder.calories = (TextView) convertView
                    .findViewById(R.id.cal);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ingredientData= data.get(position).getIngredient();
        titleData = data.get(position).getTitle();
        urlData = data.get(position).getURL();
        //String calString = Double.toString(titleData);
        //String fatString = Double.toString(urlData);
        viewHolder.searchedFood.setText("Ingredient: " + ingredientData);
        viewHolder.fat.setText("url: " + urlData);
        viewHolder.calories.setText("Title: " + titleData);
        return convertView;
    }

    /**
     * inner class to hold the view of detailed searched result
     */
    class ViewHolder {
        public TextView searchedFood, fat, calories;

    }
}