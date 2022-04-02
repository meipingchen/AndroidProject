package com.cst2335.androidproject;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * to fetch the data from Json file.
 */
public class RecipeJsonData {
    private List<RecipeNewBean> newBeanList = new ArrayList<>();

    /**
     * to get the data from the Json Object
     *
     * @param jsonUrl the URL for the connection
     * @return the data of the Json Object
     */
    public List<RecipeNewBean> getJsonData(String jsonUrl) {

        try {

            URL httpUrl = new URL(jsonUrl);

            HttpURLConnection connection = (HttpURLConnection) httpUrl
                    .openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer sb = new StringBuffer();
            String ln = "";

            while ((ln = bufferedReader.readLine()) != null) {

                sb.append(ln);
            }
            Log.e("TAG", "" + sb.toString());
            JSONObject response = new JSONObject(sb.toString());
            JSONArray hits = response.getJSONArray("hits");
            for (int i = 0; i<hits.length(); i++) {
                JSONObject hit = hits.getJSONObject(i);
                JSONObject recipe = hit.getJSONObject("recipe");
                JSONArray jsonArray = recipe.getJSONArray("ingredientLines");

                String ingredient =  jsonArray.toString();
                ingredient = ingredient.replace("[","");
                ingredient = ingredient.replace("]","");
                ingredient = ingredient.replace('"',' ');

                String title = recipe.getString("label");
                String url = recipe.getString("url");
                RecipeNewBean newBean = new RecipeNewBean();
                newBean.setIngredient(ingredient);
                newBean.setTitle(title);
                newBean.setURL(url);
                newBeanList.add(newBean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newBeanList;

    }

}
