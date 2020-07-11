package com.example.android.bakingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.data.LastIngredients;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.RecipeSaver;
import com.example.android.bakingapp.utils.NetworkUtils;
import com.example.android.bakingapp.utils.OpenRecipeJsonUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler{

    private RecyclerView mRecipesRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private TextView mErrorMessage;
    private ProgressBar mLoadingSpinner;
    boolean isTabletLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecipesRecyclerView = findViewById(R.id.rv_recipes);
        mErrorMessage = findViewById(R.id.tv_error_message);
        mLoadingSpinner = findViewById(R.id.pb_loading_spinner);


        if(findViewById(R.id.tv_tablet) != null){
            isTabletLayout = true;

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

            mRecipesRecyclerView.setLayoutManager(gridLayoutManager);
            mRecipesRecyclerView.setHasFixedSize(true);
            mRecipeAdapter = new RecipeAdapter( this);
            loadRecipes();
            mRecipesRecyclerView.setAdapter(mRecipeAdapter);
        }else{
            isTabletLayout = false;


            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mRecipesRecyclerView.setLayoutManager(layoutManager);
            mRecipeAdapter = new RecipeAdapter( this);
            loadRecipes();
            mRecipesRecyclerView.setAdapter(mRecipeAdapter);
        }
    }

    @Override
    public void onClick(int recipeId) {
        LastIngredients.setLastViewedIngredients(recipeId);

        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(getString(R.string.recipe_id), recipeId);
        intent.putExtra(getString(R.string.is_tablet_layout), isTabletLayout);
        startActivity(intent);
    }

    public class FetchRecipesTask extends AsyncTask<Void, Void, List<Recipe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Recipe> doInBackground(Void... voids) {

            URL recipeRequestUrl = NetworkUtils.buildUrl();
            String jsonRecipeResponse = NetworkUtils.getResponseFromHttpUrl(recipeRequestUrl);

            JSONObject jsonItem = null;
            List<Recipe> recipeList = new ArrayList<>();

            try {
                JSONArray simpleJsonRecipeData = OpenRecipeJsonUtils.getSimpleRecipeStringFromJson(jsonRecipeResponse);
                int arrSize = simpleJsonRecipeData.length();
                for(int i = 0; i < arrSize; i++){
                    jsonItem = simpleJsonRecipeData.getJSONObject(i);
                    Recipe recipe = new Gson().fromJson(String.valueOf(jsonItem), Recipe.class);
                    recipeList.add(recipe);
                    recipe = null;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return recipeList;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            mLoadingSpinner.setVisibility(View.INVISIBLE);
            RecipeSaver recipeSaver = RecipeSaver.getInstance();

            if(recipes.size() > 0){

                // Show recipes list if data available
                showRecipesDataView();
                recipeSaver.saveNewData(recipes);
                mRecipeAdapter.setRecipeData(recipes);
            }else{
                showErrorMessage();
            }
        }
    }

    private void showErrorMessage() {
        mRecipesRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    private void showRecipesDataView() {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecipesRecyclerView.setVisibility(View.VISIBLE);
    }

    private void loadRecipes() {
        boolean isInternetAvailable = NetworkUtils.isConnected();
        if(isInternetAvailable){
            showRecipesDataView();
            new FetchRecipesTask().execute();
        }else{
            showErrorMessage();
        }
    }

}