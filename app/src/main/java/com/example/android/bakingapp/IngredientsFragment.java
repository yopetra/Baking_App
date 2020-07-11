package com.example.android.bakingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.RecipeSaver;

import java.util.ArrayList;
import java.util.List;

public class IngredientsFragment extends Fragment {

    private List<String> name = new ArrayList<>();
    private List<String> quantity = new ArrayList<>();
    private List<String> measureType = new ArrayList<>();
    private int mRecipeId = -1;
    boolean isTablet = false;

    private IngredientsAdapter mIngredientsAdapter;

    public IngredientsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        isTablet = getArguments().getBoolean(getString(R.string.is_tablet_bundle));

        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        RecyclerView mRecyclerView = rootView.findViewById(R.id.rv_ingredients);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mIngredientsAdapter = new IngredientsAdapter();
        mRecyclerView.setAdapter(mIngredientsAdapter);

        if(isTablet){
            DetailsActivity detailsActivity = (DetailsActivity) getActivity();
            mRecipeId = detailsActivity.getAdapterPosition();
        }else{
            ExpandedDetailsActivity expandedDetailsActivity = (ExpandedDetailsActivity) getActivity();
            mRecipeId = expandedDetailsActivity.getRecipeId();
        }

        loadIngredientsData(mRecipeId);

        return rootView;
    }

    private void loadIngredientsData(int position) {
        Recipe recipe = RecipeSaver.getInstance().get(position);
        ArrayList<Recipe.Ingredients> ingredientsArray = recipe.getIngredients();
        int arrSize = ingredientsArray.size();

        String nameJ = null;
        String quantityJ = null;
        String measureTypeJ = null;

        for(int i = 0; i < arrSize; i++){
            nameJ = ingredientsArray.get(i).getIngredient();
            quantityJ = ingredientsArray.get(i).getQuantity();
            measureTypeJ = ingredientsArray.get(i).getMeasureType();

            name.add(nameJ);
            quantity.add(quantityJ);
            measureType.add(measureTypeJ);
        }

        mIngredientsAdapter.setIngredientsData(name, quantity, measureType);
    }
}
