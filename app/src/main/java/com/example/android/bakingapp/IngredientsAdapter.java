package com.example.android.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> name = new ArrayList<>();
    private List<String> quantity = new ArrayList<>();
    private List<String> measureType = new ArrayList<>();

    public IngredientsAdapter(){}

    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView ingredientNameTextView;
        public final TextView quantityTextView;
        public final TextView measureTypeTextView;

        public IngredientsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientNameTextView = itemView.findViewById(R.id.tv_ingredient_name);
            quantityTextView = itemView.findViewById(R.id.tv_quantity);
            measureTypeTextView = itemView.findViewById(R.id.tv_measure_type);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.ingredients_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParent = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup,shouldAttachToParent);
        return new IngredientsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String currentName = name.get(position);
        String currentQuantity = quantity.get(position);
        String currentMeasureType = measureType.get(position);

        IngredientsAdapterViewHolder ingredientsHolder = (IngredientsAdapterViewHolder) holder;
        ingredientsHolder.ingredientNameTextView.setText(currentName);
        ingredientsHolder.quantityTextView.setText(currentQuantity);
        ingredientsHolder.measureTypeTextView.setText(currentMeasureType);
    }

    @Override
    public int getItemCount() {
        if(name == null){return 0;}
        return name.size();
    }

    public void setIngredientsData(List<String> ingredientsName, List<String> ingrQuantity, List<String> ingrMeasure){
        int arrSize = ingredientsName.size();

        for(int i = 0; i < arrSize; i++){
            name.add(ingredientsName.get(i));
            quantity.add(ingrQuantity.get(i));
            measureType.add(ingrMeasure.get(i));
        }

        notifyDataSetChanged();
    }
}
