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
public class NutritionJsonData {
    private List<NutritionNewBean> newBeanList = new ArrayList<>();

    /**
     * to get the data from the Json Object
     *
     * @param jsonUrl the URL for the connection
     * @return the data of the Json Object
     */
    public List<NutritionNewBean> getJsonData(String jsonUrl) {

        try {
            //create http URL
            URL httpUrl = new URL(jsonUrl);
            //open URL
            HttpURLConnection connection = (HttpURLConnection) httpUrl
                    .openConnection();
            //set parameter
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer sb = new StringBuffer();
            String str = "";

            while ((str = bufferedReader.readLine()) != null) {

                sb.append(str);
            }
            Log.e("TAG", "" + sb.toString());
            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("hints");
            JSONObject textArray = jsonArray.getJSONObject(0);
            JSONObject foodObject = textArray.getJSONObject("food");
            JSONObject nutrients = foodObject.getJSONObject("nutrients");
            double calories = (double) foodObject.getJSONObject("nutrients").get("ENERC_KCAL");
            double fat = (double) foodObject.getJSONObject("nutrients").get("FAT");

            NutritionNewBean newBean = new NutritionNewBean();
            newBean.setCalories(calories);
            newBean.setFat(fat);
            newBeanList.add(newBean);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newBeanList;

    }

}
