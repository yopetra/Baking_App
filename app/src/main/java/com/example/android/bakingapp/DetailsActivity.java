package com.example.android.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.RecipeSaver;

public class DetailsActivity extends AppCompatActivity implements DetailsFragment.OnDetailsItemClickListener{

    private int recipePosition = -1;
    private boolean isTabletLayout = false;
    private int mPositionDetails = -1;
    Bundle mInstanceState;
    private final static String POSITION_DETAILS_KEY = "position_details_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstanceState = savedInstanceState;
        Intent intent = getIntent();
        recipePosition = intent.getIntExtra(getString(R.string.recipe_id), -1);
        isTabletLayout = intent.getBooleanExtra(getString(R.string.is_tablet_layout), false);
        setContentView(R.layout.activity_details);

        if(savedInstanceState == null){
            if(isTabletLayout){
                FragmentManager fragmentManager = getSupportFragmentManager();
                DetailsFragment detailsFragment = new DetailsFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.fl_details_list, detailsFragment)
                        .commit();

                Bundle bundle = new Bundle();
                bundle.putBoolean(getString(R.string.is_tablet_bundle), isTabletLayout);

                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .add(R.id.fl_ingred_and_steps, ingredientsFragment)
                        .commit();

                setNameOfRecipeInActionbar();
            }else{

                FragmentManager fragmentManager = getSupportFragmentManager();
                DetailsFragment detailsFragment = new DetailsFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.detailds_list_frame, detailsFragment)
                        .commit();

                setNameOfRecipeInActionbar();
            }
        }else{

            // restore position details
            if(savedInstanceState.containsKey(POSITION_DETAILS_KEY)){
                mPositionDetails = savedInstanceState.getInt(POSITION_DETAILS_KEY);
            }
        }
    }

    private void setNameOfRecipeInActionbar() {
        // Set name of the cake to ActionBar
        String recipeName = RecipeSaver.getInstance().get(recipePosition).getName();
        getSupportActionBar().setTitle(recipeName);
    }

    @Override
    public void onItemSelected(int positionDetails) {
        mPositionDetails = positionDetails;
        Recipe recipe = RecipeSaver.getInstance().get(recipePosition);
        int recipeStepsSize = recipe.getStepsSize();

        if(isTabletLayout){
            if(positionDetails == 0){
                if(mInstanceState == null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(getString(R.string.is_tablet_bundle), isTabletLayout);

                    IngredientsFragment ingredientsFragment = new IngredientsFragment();
                    ingredientsFragment.setArguments(bundle);

                    fragmentManager.beginTransaction()
                            .replace(R.id.fl_ingred_and_steps, ingredientsFragment)
                            .commit();
                }
            }else{
                if(mInstanceState == null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(getString(R.string.is_tablet_bundle), isTabletLayout);

                    StepFragment stepFragment = new StepFragment();
                    stepFragment.setArguments(bundle);

                    fragmentManager.beginTransaction()
                            .replace(R.id.fl_ingred_and_steps, stepFragment)
                            .commit();
                }
            }
        }else{
            Intent intent = new Intent(DetailsActivity.this, ExpandedDetailsActivity.class);
            intent.putExtra(getString(R.string.adapter_position), positionDetails);
            intent.putExtra(getString(R.string.recipe_id), recipePosition);
            intent.putExtra(getString(R.string.recipe_steps_size), recipeStepsSize);
            intent.putExtra(getString(R.string.is_tablet_layout), isTabletLayout);
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION_DETAILS_KEY, mPositionDetails);
    }

    public int getAdapterPosition(){
        return recipePosition;
    }

    public int getPositionDetails(){
        return mPositionDetails;
    }
}