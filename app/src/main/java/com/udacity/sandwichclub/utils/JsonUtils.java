package com.udacity.sandwichclub.utils;

import android.widget.Toast;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich parsedSandwich = null;
        try {
            //Extract all the sandwich data
            JSONObject jsonData = new JSONObject(json);
            JSONObject name = jsonData.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAsJSON = name.getJSONArray("alsoKnownAs");
            //Covert JSONArray in arrayList
            List<String> alsoKnownAs = new ArrayList<String>();
            for (int i = 0; i < alsoKnownAsJSON.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJSON.getString(i));
            }
            String placeOfOrigin = jsonData.getString("placeOfOrigin");
            //If no place is available, put unknown
            if (placeOfOrigin.equals("")) {
                placeOfOrigin = "Unknown";
            }
            String description = jsonData.getString("description");
            String image = jsonData.getString("image");

            JSONArray ingredientsJSON = jsonData.getJSONArray("ingredients");
            //Covert JSONArray in arrayList
            List<String> ingredients = new ArrayList<String>();
            for (int i = 0; i < ingredientsJSON.length(); i++) {
                ingredients.add(ingredientsJSON.getString(i));
            }
            //Create the sandwich object
            parsedSandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return parsedSandwich;
    }
}
