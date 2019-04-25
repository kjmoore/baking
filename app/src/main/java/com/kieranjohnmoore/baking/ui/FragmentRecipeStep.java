package com.kieranjohnmoore.baking.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class FragmentRecipeStep extends Fragment {
    private static final String TAG = FragmentRecipeStep.class.getSimpleName();

    public FragmentRecipeStep() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final FragmentRecipeStepBinding viewBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_step, container, false);

        final Bundle bundle = this.getArguments();
        Log.e(TAG, "KM" +bundle);

        if (bundle == null || !bundle.containsKey(ActivityMain.DATA_RECIPE_ID) ||
                !bundle.containsKey(ActivityMain.DATA_RECIPE_STEP)) {
            Log.e(TAG, "Could not detect recipe step to load");
            return viewBinding.getRoot();
        }

        int recipeNumber = bundle.getInt(ActivityMain.DATA_RECIPE_ID);
        int recipeStep = bundle.getInt(ActivityMain.DATA_RECIPE_STEP);
        Log.d(TAG, "Loading recipe: " + recipeNumber + " step: " + recipeStep);

        final SharedViewModel viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SharedViewModel.class);
        List<Recipe> recipes = viewModel.getRecipes().getValue();
        if (recipes != null && recipes.size() > recipeNumber) {
            final Recipe recipe = recipes.get(recipeNumber);
            final RecipeStep step = recipe.steps.get(recipeStep);

            viewBinding.setStep(step);
        }

        return viewBinding.getRoot();
    }
}
