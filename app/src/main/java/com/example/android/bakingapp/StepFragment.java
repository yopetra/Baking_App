package com.example.android.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.RecipeSaver;
import com.example.android.bakingapp.utils.ExoplayerUtils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

public class StepFragment extends Fragment {

    TextView descriptionTextView;
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    String description = null;
    String videoUrl = null;
    private boolean isTablet;

    public StepFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isTablet = getArguments().getBoolean(getString(R.string.is_tablet_bundle));

        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        descriptionTextView = rootView.findViewById(R.id.tv_recipe_step_instructions);
        mPlayerView = rootView.findViewById(R.id.player_view);

        int recipeId;
        if(isTablet){
            DetailsActivity detailsActivity = (DetailsActivity) getActivity();
            recipeId = detailsActivity.getAdapterPosition();
            int positionDetails = detailsActivity.getPositionDetails();
            loadStepsData(recipeId, positionDetails);
        }else{
            ExpandedDetailsActivity expandedDetailsActivity = (ExpandedDetailsActivity) getActivity();
            recipeId = expandedDetailsActivity.getRecipeId();
            int positionDetails = expandedDetailsActivity.getPositionDetails();
            loadStepsData(recipeId, positionDetails);
        }



        // Check if video available
        if(videoUrl.length() > 0){
            mPlayerView.setVisibility(View.VISIBLE);
            // Initialize the player.
            initializePlayer(Uri.parse(videoUrl));
        }else{
            mPlayerView.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(videoUrl.length() > 0){
            releasePlayer();
        }
    }

    private void loadStepsData(int recipeId, int positionDetails) {
        Recipe recipe = RecipeSaver.getInstance().get(recipeId);
        Recipe.Steps step = recipe.getStepById(positionDetails);

        description = step.getDescription();
        videoUrl = step.getVideoURL();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT || isTablet){
            descriptionTextView.setText(description);
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if(mExoPlayer == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            Context context = getContext();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = ExoplayerUtils.getUserAgent(context, "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }
}
