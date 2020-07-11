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

public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mDescrNames = new ArrayList<>();
    public DetailsAdapter(){  }

    public class DetailsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mDetailsTextView;

        public DetailsAdapterViewHolder(View view){
            super(view);
            mDetailsTextView = view.findViewById(R.id.tv_step_description);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int adapterPositionDetails = getAdapterPosition();
            DetailsFragment.getMCallbacks().onItemSelected(adapterPositionDetails);
        }
    }

    @Override
    public DetailsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.step_descr_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImediatelly = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImediatelly);
        return new DetailsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String currentDescription = mDescrNames.get(position);
        DetailsAdapterViewHolder detailsHolder = (DetailsAdapterViewHolder) holder;
        detailsHolder.mDetailsTextView.setText(currentDescription);
    }

    @Override
    public int getItemCount(){
        if(mDescrNames == null){return 0;}
        return mDescrNames.size();
    }

    public void setDescriptionData(List<String> descriptionData){
        int arrSize = descriptionData.size();
        for(int i = 0; i < arrSize; i++){
            mDescrNames.add(descriptionData.get(i));
        }
        notifyDataSetChanged();
    }
}
