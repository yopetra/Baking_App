package com.example.android.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.android.bakingapp.data.RecipeSaver;

public class ExpandedDetailsActivity extends AppCompatActivity {

    private int positionDetails = -1;
    int recipeId = -1;
    int recipeStepSize = 0;
    private boolean isTabletLayout;
    Button prevButton;
    Button nextButton;
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_details);
        Intent intent = getIntent();
        positionDetails = intent.getExtras().getInt(getString(R.string.adapter_position));
        recipeId = intent.getExtras().getInt(getString(R.string.recipe_id));
        recipeStepSize = intent.getExtras().getInt(getString(R.string.recipe_steps_size));
        isTabletLayout = intent.getExtras().getBoolean("isTablet", false);

        prevButton = findViewById(R.id.bt_prev_step);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionDetails > 1){
                    positionDetails--;
                    openNextStep();
                }

                if(positionDetails == 1){
                    prevButton.setVisibility(View.INVISIBLE);
                }else{
                    prevButton.setVisibility(View.VISIBLE);
                }
            }
        });

        nextButton = findViewById(R.id.bt_next_step);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionDetails < recipeStepSize){
                    positionDetails++;
                    openNextStep();
                }

                if(positionDetails == recipeStepSize){
                    nextButton.setVisibility(View.INVISIBLE);
                }else{
                    nextButton.setVisibility(View.VISIBLE);
                }
            }
        });

        if(positionDetails == 0){

            hidePrevNextButtons();
            if(savedInstanceState == null){
                Bundle bundle = new Bundle();
                bundle.putBoolean(getString(R.string.is_tablet_bundle), isTabletLayout);

                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setArguments(bundle);


                fragmentManager.beginTransaction()
                        .add(R.id.expanded_details_container, ingredientsFragment)
                        .commit();
            }
        }else{
            if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE && !isTabletLayout){
                showPrevNextButtons();
            }else{
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getSupportActionBar().hide();
                hidePrevNextButtons();
            }

            if(savedInstanceState == null){
                Bundle bundle = new Bundle();
                bundle.putBoolean(getString(R.string.is_tablet_bundle), isTabletLayout);

                StepFragment stepFragment = new StepFragment();
                stepFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .add(R.id.expanded_details_container, stepFragment)
                        .commit();
            }
        }

        hideNavigateButtonIfNeed(positionDetails);

        // Set name of the cake to ActionBar
        String recipeName = RecipeSaver.getInstance().get(recipeId).getName();
        getSupportActionBar().setTitle(recipeName);
    }

    private void hideNavigateButtonIfNeed(int positionDetails) {

        if(positionDetails == 1){
            prevButton.setVisibility(View.INVISIBLE);
        }

        if(positionDetails == recipeStepSize){
            nextButton.setVisibility(View.INVISIBLE);
        }
    }

    private void hidePrevNextButtons() {
        prevButton.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.INVISIBLE);
    }

    private void showPrevNextButtons() {
        prevButton.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);
    }

    private void openNextStep() {
        prevButton.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);

        Bundle bundle = new Bundle();
        bundle.putBoolean(getString(R.string.is_tablet_bundle), isTabletLayout);

        StepFragment stepFragment = new StepFragment();
        stepFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.expanded_details_container, stepFragment)
                .commit();
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getPositionDetails(){return positionDetails;}
}