package com.kieranjohnmoore.baking.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kieranjohnmoore.baking.R;
import com.kieranjohnmoore.baking.databinding.FragmentRecipeListBinding;
import com.kieranjohnmoore.baking.model.Recipe;
import com.kieranjohnmoore.baking.viewmodel.SharedViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

public class RecipeListFragment extends Fragment {
    private static final String TAG = RecipeListFragment.class.getSimpleName();

    private FragmentRecipeListBinding viewBinding;
    private RecipeListRecyclerView recipeListRecyclerView = new RecipeListRecyclerView();

    public RecipeListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_list, container, false);

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        viewBinding.recipesView.setLayoutManager(layoutManager);
        viewBinding.recipesView.setAdapter(recipeListRecyclerView);

        final SharedViewModel viewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
        viewModel.getRecipes().observe(this, this::onRecipesDownloaded);

        return viewBinding.getRoot();
    }

    private void onRecipesDownloaded(List<Recipe> recipes) {
        Log.v(TAG, "Downloaded data: " + recipes);
        recipeListRecyclerView.updateRecipes(recipes);
        viewBinding.noData.setVisibility(View.GONE);
    }
}
