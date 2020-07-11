package com.example.android.bakingapp.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LastIngredients {

    private static List<String> mLastIngredients = new ArrayList<>(Arrays.asList(
            "To add",
            "ingredients",
            "in this widget",
            "open any",
            "recipe"));

    public static ArrayList<String> getData(){
        return (ArrayList<String>) mLastIngredients;
    }

    public static void setLastViewedIngredients(int recipeId){
        Recipe recipe = RecipeSaver.getInstance().get(recipeId);
        ArrayList<Recipe.Ingredients> ingredients = recipe.getIngredients();

        mLastIngredients.clear();

        int arrSize = ingredients.size();
        for(int i = 0; i < arrSize; i++){
            String name = ingredients.get(i).getIngredient();
            String quantity = ingredients.get(i).getQuantity();
            String type = ingredients.get(i).getMeasureType();

            String lastIngredientItem = name + ": " + quantity + " " + type;
            mLastIngredients.add(lastIngredientItem);
        }
    }
}
