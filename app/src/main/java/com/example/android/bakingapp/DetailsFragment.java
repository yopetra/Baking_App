package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.RecipeSaver;

import java.util.ArrayList;
import java.util.List;


public class DetailsFragment extends Fragment {

    List<String> detailNames = new ArrayList<>();
    private DetailsAdapter mRecipeDelailsAdapter;
    int position = -1;

    static OnDetailsItemClickListener mCallbacks;

    public static OnDetailsItemClickListener getMCallbacks(){
        return mCallbacks;
    };

    public interface OnDetailsItemClickListener{
        void onItemSelected(int position);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            mCallbacks = (OnDetailsItemClickListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnDetailsItemClickListener");
        }
    }

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        RecyclerView mRecyclerView = rootView.findViewById(R.id.rv_step_description_in_fragment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecipeDelailsAdapter = new DetailsAdapter();
        mRecyclerView.setAdapter(mRecipeDelailsAdapter);

        DetailsActivity detailsActivity = (DetailsActivity) getActivity();
        position = detailsActivity.getAdapterPosition();
        loadDescriptionData(position);

        return rootView;
    }

    private void loadDescriptionData(int position) {

        // This is a top item in list which everytime named as 'Recipe ingredients'
        detailNames.add("Recipe ingredients");

        Recipe recipe = RecipeSaver.getInstance().get(position);
        ArrayList<Recipe.Steps> jsonSteps = recipe.getSteps();
        int arrSize = jsonSteps.size();

        // Fill arrayList with names of steps
        for(int i = 0; i < arrSize; i++){
            Recipe.Steps step = jsonSteps.get(i);
            String name = step.getShortDescription();
            detailNames.add(name);
        }

        mRecipeDelailsAdapter.setDescriptionData(detailNames);
    }
}