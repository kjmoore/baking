package com.kieranjohnmoore.baking.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kieranjohnmoore.baking.R;
import com.kieranjohnmoore.baking.databinding.FragmentRecipeStepBinding;
import com.kieranjohnmoore.baking.viewmodel.SharedViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class FragmentRecipeStep extends Fragment {
    private static final String TAG = FragmentRecipeStep.class.getSimpleName();

    private SharedViewModel viewModel;

    public FragmentRecipeStep() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentRecipeStepBinding viewBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_step, container, false);

        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SharedViewModel.class);
        viewModel.getStep().observe(this, (step) -> {
            Log.d(TAG, "Setting step: " + step);
            viewBinding.setStep(step);
        });

        return viewBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        viewModel.getStep().removeObservers(this);
    }
}
