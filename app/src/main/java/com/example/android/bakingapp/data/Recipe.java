package com.example.android.bakingapp.data;

import java.util.ArrayList;

public class Recipe {

    private int id;
    private String name;
    private ArrayList<Ingredients> ingredients;
    private ArrayList<Steps> steps;

    public Recipe(){}

    public Recipe(int id, String name, ArrayList<Ingredients> ingredients, ArrayList<Steps> steps){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    public Steps getStepById(int stepId){
        return this.steps.get(stepId - 1);
    }

    public int getStepsSize(){return steps.size();}

    public void setSteps(ArrayList<Steps> steps) {
        this.steps = steps;
    }

    public class Steps{
        private int id;
        private String shortDescription;
        private String description;
        private String videoURL;

        public Steps(int id, String shortDescription, String description, String videoURL){
            this.id = id;
            this.shortDescription = shortDescription;
            this.description = description;
            this.videoURL = videoURL;
        }

        public int getId() {
            return id;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public String getDescription() {
            return description;
        }

        public String getVideoURL() {
            return videoURL;
        }

    }

    public class Ingredients{
        private String ingredient;
        private String quantity;
        private String measure;

        public Ingredients(String name, String quantity, String measureType){
            this.ingredient = name;
            this.quantity = quantity;
            this.measure = measureType;
        }

        public String getIngredient() {
            return ingredient;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getMeasureType() {
            return measure;
        }
    }
}
