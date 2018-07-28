package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        String mainNameKey = "mainName";
        String alsoKnownAsKey = "alsoKnownAs";
        String placeOfOriginKey = "placeOfOrigin";
        String descriptionKey = "description";
        String imageKey = "image";
        String ingredientsKey = "ingredients";
        Sandwich sandwich = null;
        try {

            JSONObject sandWishInfo = new JSONObject(json);
            String mainName = sandWishInfo.getJSONObject("name").getString(mainNameKey);

            JSONArray alsoKnowAsJsonArray = sandWishInfo.getJSONObject("name").getJSONArray(alsoKnownAsKey);
            List<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnowAsJsonArray.length(); i++) {
                alsoKnownAs.add(alsoKnowAsJsonArray.getString(i));
            }

            String placeOfOrigin = sandWishInfo.getString(placeOfOriginKey);
            String description = sandWishInfo.getString(descriptionKey);
            String image = sandWishInfo.getString(imageKey);

            List<String> ingredients = new ArrayList<>();
            JSONArray ingredientJsonArray = sandWishInfo.getJSONArray(ingredientsKey);
            for (int i = 0; i < ingredientJsonArray.length(); i++) {
                ingredients.add(ingredientJsonArray.getString(i));
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {

            return sandwich;
        }
    }
}
