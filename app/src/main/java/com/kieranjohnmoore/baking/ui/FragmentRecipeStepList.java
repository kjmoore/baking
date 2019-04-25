package com.kieranjohnmoore.baking.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kieranjohnmoore.baking.R;
import com.kieranjohnmoore.baking.databinding.FragmentRecipeBinding;
import com.kieranjohnmoore.baking.viewmodel.SharedViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

public class FragmentRecipeStepList extends Fragment {
    private static final String TAG = FragmentRecipeStepList.class.getSimpleName();

    private final RecipeStepRecyclerView recipeStepRecyclerView = new RecipeStepRecyclerView();
    private SharedViewModel viewModel;

    public FragmentRecipeStepList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final FragmentRecipeBinding viewBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false);

        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SharedViewModel.class);
        viewModel.getRecipe().observe(this, (recipe) -> {
            Log.d(TAG, "Viewing recipe: " + recipe);
            viewBinding.setRecipe(recipe);
            recipeStepRecyclerView.setSteps(recipe.steps);
        });

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        viewBinding.recipeList.setLayoutManager(layoutManager);
        viewBinding.recipeList.setAdapter(recipeStepRecyclerView);

        viewBinding.noData.setVisibility(View.GONE);

        return viewBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        viewModel.getRecipe().removeObservers(this);
    }
}
