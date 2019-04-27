package com.kieranjohnmoore.baking.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.kieranjohnmoore.baking.R;
import com.kieranjohnmoore.baking.databinding.FragmentRecipeStepBinding;
import com.kieranjohnmoore.baking.model.Recipe;
import com.kieranjohnmoore.baking.model.RecipeStep;
import com.kieranjohnmoore.baking.viewmodel.SharedViewModel;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class FragmentRecipeStep extends Fragment implements ExoPlayer.EventListener {
    private static final String TAG = FragmentRecipeStep.class.getSimpleName();

    private FragmentRecipeStepBinding viewBinding;
    private SharedViewModel viewModel;
    private SimpleExoPlayer mediaPlayer;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder mediaPlayerState;

    public FragmentRecipeStep() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         viewBinding =
                 DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_step, container, false);

        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SharedViewModel.class);
        viewModel.getStep().observe(this, (step) -> {
            Log.d(TAG, "Setting step: " + step);
            viewBinding.setStep(step);
            if (step.videoURL != null && !step.videoURL.isEmpty()) {
                final Uri videoUri = Uri.parse(step.videoURL);
                Log.d(TAG, "Playing video: " + videoUri);
                initializePlayer(videoUri);
                initializeMediaSession();
            } else {
                viewBinding.playerView.setVisibility(View.GONE);
            }

            if (viewBinding.buttonBack != null) {
                if (step.id == 0) {
                    viewBinding.buttonBack.setEnabled(false);
                } else {
                    viewBinding.buttonBack.setEnabled(true);
                }

                viewBinding.buttonBack.setOnClickListener((view) -> {
                    navigate(view, step.id - 1);
                });
            }

            if (viewBinding.buttonForward != null) {
                final Recipe recipes = viewModel.getRecipe().getValue();
                if (recipes != null) {
                    if (step.id < (recipes.steps.size() - 1)) {
                        viewBinding.buttonForward.setEnabled(true);
                    } else {
                        viewBinding.buttonForward.setEnabled(false);
                    }

                    viewBinding.buttonForward.setOnClickListener((view) -> {
                        navigate(view, step.id + 1);
                    });
                }
            }

        });

        return viewBinding.getRoot();
    }

    private void navigate(View view, int step) {
        final Intent intent = new Intent(ActivityRecipe.VIEW_STEP);
        intent.putExtra(ActivityRecipe.DATA_RECIPE_STEP, step);
        LocalBroadcastManager.getInstance(view.getContext()).sendBroadcast(intent);
    }

    private void initializePlayer(Uri mediaUri) {
        if (mediaPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mediaPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext(), trackSelector, loadControl);
            viewBinding.playerView.setPlayer(mediaPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(this.getContext(), "Baking");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    Objects.requireNonNull(this.getContext()), userAgent),
                    new DefaultExtractorsFactory(), null, null);
            mediaPlayer.prepare(mediaSource);
            mediaPlayer.setPlayWhenReady(false);

            mediaPlayer.addListener(this);
        }
    }

    private void initializeMediaSession() {
        mediaSession = new MediaSessionCompat(Objects.requireNonNull(this.getContext()), TAG);
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);
        mediaPlayerState = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(mediaPlayerState.build());
        mediaSession.setCallback(new MediaSessionCallbacks());
        mediaSession.setActive(true);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) { }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) { }

    @Override
    public void onLoadingChanged(boolean isLoading) { }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mediaPlayerState.setState(PlaybackStateCompat.STATE_PLAYING,
                    mediaPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mediaPlayerState.setState(PlaybackStateCompat.STATE_PAUSED,
                    mediaPlayer.getCurrentPosition(), 1f);
        }
        mediaSession.setPlaybackState(mediaPlayerState.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) { }

    @Override
    public void onPositionDiscontinuity() { }

    private class MediaSessionCallbacks extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mediaPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mediaPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mediaPlayer.seekTo(0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (mediaSession != null) {
            mediaSession.setActive(false);
        }

        viewModel.getStep().removeObservers(this);
    }
}
