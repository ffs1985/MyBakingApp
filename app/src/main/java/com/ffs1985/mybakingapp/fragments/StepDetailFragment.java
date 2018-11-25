package com.ffs1985.mybakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ffs1985.mybakingapp.R;
import com.ffs1985.mybakingapp.model.RecipesViewModel;
import com.ffs1985.mybakingapp.model.Step;
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
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment {
    @BindView(R.id.step_description) TextView tvDescription;
    @BindView(R.id.playerView) SimpleExoPlayerView mPlayerView;
    private RecipesViewModel recipesViewModel;
    private SimpleExoPlayer mExoPlayer;
    private Step step;

    public StepDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, view);

        recipesViewModel = RecipesViewModel.getInstance();
        step = recipesViewModel.getStep();
        setStepInfo();
        initializePlayer();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {

    }

    public void setStep(Step step) {
        this.step = step;
    }

    private void initializePlayer() {
        String videoUrl = this.step.getVideoURL();
        if(videoUrl != null && !videoUrl.isEmpty()) {
            Uri mediaUri = Uri.parse(videoUrl);
            if (mExoPlayer == null) {
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
                mPlayerView.setPlayer(mExoPlayer);
                String userAgent = Util.getUserAgent(getActivity(), "MyBakingApp");
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                        new DefaultDataSourceFactory(
                        getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(false);
            }
        } else {
            mPlayerView.setVisibility(View.GONE);
        }
    }

    private void releasePlayer() {
        if(mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void setStepInfo() {
        this.tvDescription.setText(step.getDescription());
        getActivity().setTitle(step.getShortDescription());
    }
}
