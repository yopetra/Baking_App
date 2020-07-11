package com.example.android.bakingapp.data;

import java.util.ArrayList;
import java.util.List;

public class RecipeSaver {

    private static RecipeSaver instance = null;
    private static List<Recipe> recipeList = new ArrayList<>();

    private RecipeSaver(){}

    public static RecipeSaver getInstance(){
        if(instance == null){
            instance = new RecipeSaver();
        }

        return instance;
    }

    public Recipe get(int position) {
        return recipeList.get(position);
    }

    public void saveNewData(List<Recipe> recipes) {
        clearData();
        copyArray(recipes, recipeList);
    }

    public int size() {
        return recipeList.size();
    }

    private void clearData(){
        while( recipeList.size() > 0 ){
            recipeList.remove(0);
        }
    }

    private void copyArray(List<Recipe> sourse, List<Recipe> destination){
        int arrSize = sourse.size();

        for(int i = 0; i < arrSize; i++){
            destination.add(sourse.get(i));
        }
    }
}
