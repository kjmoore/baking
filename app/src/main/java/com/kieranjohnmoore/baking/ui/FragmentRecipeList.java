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
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

public class FragmentRecipeList extends Fragment {
    private static final String TAG = FragmentRecipeList.class.getSimpleName();

    private RecipeListRecyclerView recipeListRecyclerView = new RecipeListRecyclerView();

    private FragmentRecipeListBinding viewBinding;

    public FragmentRecipeList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_list, container, false);

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        viewBinding.recipeList.setLayoutManager(layoutManager);
        viewBinding.recipeList.setAdapter(recipeListRecyclerView);

        final SharedViewModel viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SharedViewModel.class);
                viewModel.getRecipes().observe(this, this::onRecipesDownloaded);

        int recipeNumber = 1;
        Log.d(TAG, "Loading recipe: " + recipeNumber);

        return viewBinding.getRoot();
    }

    private void onRecipesDownloaded(List<Recipe> recipes) {
        Log.v(TAG, "Downloaded data: " + recipes);
        recipeListRecyclerView.updateRecipes(recipes);
        viewBinding.noData.setVisibility(View.GONE);
    }
    //TODO: Loading spinner
}
