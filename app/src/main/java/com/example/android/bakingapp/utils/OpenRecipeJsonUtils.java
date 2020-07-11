package com.example.android.bakingapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OpenRecipeJsonUtils {
    public static JSONArray getSimpleRecipeStringFromJson(String recipeJsonString) throws JSONException {

//        JSONArray jsonRecipeData = null;
//        JSONObject recipeJson = new JSONObject(recipeJsonString);
        JSONArray recipeArray = new JSONArray(recipeJsonString);

        return recipeArray;
    }
}
