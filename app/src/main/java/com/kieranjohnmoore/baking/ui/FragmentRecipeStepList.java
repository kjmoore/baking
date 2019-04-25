package com.kieranjohnmoore.baking.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kieranjohnmoore.baking.R;
import com.kieranjohnmoore.baking.databinding.FragmentRecipeStepsBinding;
import com.kieranjohnmoore.baking.model.Recipe;
import com.kieranjohnmoore.baking.model.RecipeStep;
import com.kieranjohnmoore.baking.viewmodel.SharedViewModel;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

public class FragmentRecipeStepList extends Fragment {
    private static final String TAG = FragmentRecipeStepList.class.getSimpleName();

    private final RecipeStepRecyclerView recipeStepRecyclerView = new RecipeStepRecyclerView();

    public FragmentRecipeStepList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final FragmentRecipeStepsBinding viewBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_steps, container, false);

        final Bundle bundle = this.getArguments();


        if (bundle == null || !bundle.containsKey(ActivityMain.DATA_RECIPE_ID)) {
            Log.e(TAG, "Could not detect recipe to load");
            return viewBinding.getRoot();
        }

        int recipeNumber = bundle.getInt(ActivityMain.DATA_RECIPE_ID);
        Log.d(TAG, "Loading recipe: " + recipeNumber);

        final SharedViewModel viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SharedViewModel.class);
        List<Recipe> recipes = viewModel.getRecipes().getValue();
        if (recipes != null && recipes.size() > recipeNumber) {
            final Recipe recipe = recipes.get(recipeNumber);
            Log.e(TAG, "Selecting recipe: " + recipe);
            final List<RecipeStep> steps = recipe.steps;
            recipeStepRecyclerView.setSteps(steps);
        }

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        viewBinding.recipeList.setLayoutManager(layoutManager);
        viewBinding.recipeList.setAdapter(recipeStepRecyclerView);

        viewBinding.noData.setVisibility(View.GONE);

        return viewBinding.getRoot();
    }
}
