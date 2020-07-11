package com.example.android.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.RecipeSaver;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler{
        void onClick(int adapterPosition);
    }

    RecipeSaver mRecipeSaver = RecipeSaver.getInstance();
    private List<Recipe> mRecipeData = new ArrayList<>();

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIfForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParent = false;

        View view = inflater.inflate(layoutIfForListItem, viewGroup, shouldAttachToParent);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int position) {
        String currentNameOfRecipe = null;

        Recipe currentRecipe = mRecipeSaver.get(position);
        currentNameOfRecipe = currentRecipe.getName();

        recipeViewHolder.recipeItemTextView.setText(currentNameOfRecipe);
    }

    @Override
    public int getItemCount() {
        return mRecipeSaver.size();
    }

    public void setRecipeData(List<Recipe> recipeData){
        int arraySize = recipeData.size();
        for(int i = 0; i < arraySize; i++){
            Recipe currentRecipeItem = recipeData.get(i);
            mRecipeData.add(currentRecipeItem);
        }

        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView recipeItemTextView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeItemTextView = itemView.findViewById(R.id.tv_recipe_item);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();

            mClickHandler.onClick(adapterPosition);
        }
    }
}
