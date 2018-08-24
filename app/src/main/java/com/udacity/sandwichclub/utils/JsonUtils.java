package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        if (json != null && !json.isEmpty()) {
            try {
                JSONObject sandwichDetailAsJSON = new JSONObject(json);
                String mainName = sandwichDetailAsJSON.getJSONObject("name").getString("mainName");
                ArrayList<String> alsoKnownAs = new ArrayList<>();
                JSONArray jsonArrayAlsoKnownAs = sandwichDetailAsJSON.getJSONObject("name").getJSONArray("alsoKnownAs");
                if (jsonArrayAlsoKnownAs != null) {
                    for (int i = 0; i < jsonArrayAlsoKnownAs.length(); i++) {
                        alsoKnownAs.add(jsonArrayAlsoKnownAs.getString(i));
                    }
                }
                String placeOfOrigin = sandwichDetailAsJSON.getString("placeOfOrigin");
                String description = sandwichDetailAsJSON.getString("description");
                String image = sandwichDetailAsJSON.getString("image");
                ArrayList<String> ingredients = new ArrayList<>();
                JSONArray jsonArrayIngredients = sandwichDetailAsJSON.getJSONArray("ingredients");
                if (jsonArrayIngredients != null) {
                    for (int i = 0; i < jsonArrayIngredients.length(); i++) {
                        ingredients.add(jsonArrayIngredients.getString(i));
                    }
                }
                sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return sandwich;
    }
}
