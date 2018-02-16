package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String jsonStr) {
        Sandwich sandwich = null;
        try {
            Log.d(LOG_TAG, "JSON parse Start.");
            sandwich = fromJson(jsonStr);
            Log.d(LOG_TAG, "JSON parse Successed.");
        } catch (JSONException e) {
            Log.d(LOG_TAG, "JSON parse Failed."+e.getMessage());
        }
        return sandwich;//
    }

    private static Sandwich fromJson(final String jsonStr) throws JSONException {
        //used to hold Strings of sandwich alsoKnownAs
        ArrayList<String> listAlsoKnownAs = new ArrayList<>();
        //used to hold Strings of sandwich Ingredients
        ArrayList<String> listIngredients = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONObject nameObject = jsonObject.getJSONObject("name");

        //sandwich mainName
        String mainName = nameObject.getString("mainName");
        //sandwich alsoKnownAs
        JSONArray alsoKnownAs = nameObject.getJSONArray("alsoKnownAs");
        if (alsoKnownAs.length() > 0) {
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                listAlsoKnownAs.add(alsoKnownAs.getString(i));
            }
        }
        //sandwich placeOfOrigin
        String placeOfOrigin = jsonObject.getString("placeOfOrigin");
        //sandwich description
        String description = jsonObject.getString("description");
        //sandwich image
        String image = jsonObject.getString("image");
        //sandwich ingredients
        JSONArray ingredients = jsonObject.getJSONArray("ingredients");
        if (ingredients.length() > 0) {
            for (int i = 0; i < ingredients.length(); i++) {
                listIngredients.add(ingredients.getString(i));
            }
        }

         //create and return the sandwitch object
        return new Sandwich(mainName, listAlsoKnownAs, placeOfOrigin, description, image, listIngredients);
    }
}
