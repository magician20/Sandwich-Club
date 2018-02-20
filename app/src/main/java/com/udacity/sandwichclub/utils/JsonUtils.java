package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();
    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";

    //created this mid func to just be able to focus on fetching json string alone also to reuse the code
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
        JSONObject nameObject = jsonObject.getJSONObject(KEY_NAME);//optJSONObject() here retun null if not exist

        //sandwich mainName  //optString() return empty string so u not have to handle exceptions
        String mainName = nameObject.getString(KEY_MAIN_NAME);
        //sandwich alsoKnownAs
        JSONArray alsoKnownAs = nameObject.getJSONArray(KEY_ALSO_KNOWN_AS);
        if (alsoKnownAs.length() > 0) {
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                listAlsoKnownAs.add(alsoKnownAs.getString(i));
            }
        }
        //sandwich placeOfOrigin
        String placeOfOrigin = jsonObject.getString(KEY_PLACE_OF_ORIGIN);
        //sandwich description
        String description = jsonObject.getString(KEY_DESCRIPTION);
        //sandwich image
        String image = jsonObject.getString(KEY_IMAGE);
        //sandwich ingredients
        JSONArray ingredients = jsonObject.getJSONArray(KEY_INGREDIENTS);
        if (ingredients.length() > 0) {
            for (int i = 0; i < ingredients.length(); i++) {
                listIngredients.add(ingredients.getString(i));
            }
        }

         //create and return the sandwitch object
        return new Sandwich(mainName, listAlsoKnownAs, placeOfOrigin, description, image, listIngredients);
    }
}
