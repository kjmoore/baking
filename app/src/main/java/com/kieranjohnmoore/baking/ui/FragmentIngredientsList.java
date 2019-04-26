package com.kieranjohnmoore.baking.ui;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kieranjohnmoore.baking.R;
import com.kieranjohnmoore.baking.databinding.FragmentIngredientsListBinding;
import com.kieranjohnmoore.baking.model.Recipe;
import com.kieranjohnmoore.baking.viewmodel.SharedViewModel;
import com.kieranjohnmoore.baking.widget.IngredientsListWidget;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

public class FragmentIngredientsList extends Fragment {
    private SharedViewModel viewModel;
    private FragmentIngredientsListBinding viewBinding;
    private final IngredientsListRecyclerView ingredientsListRecyclerView = new IngredientsListRecyclerView();
    private Recipe recipe;

    public FragmentIngredientsList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredients_list, container, false);

        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SharedViewModel.class);
        viewModel.getRecipe().observe(this, (recipe) -> {
            this.recipe = recipe;
            ingredientsListRecyclerView.setIngredients(recipe.ingredients);
        });

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        viewBinding.ingredientsList.setLayoutManager(layoutManager);
        viewBinding.ingredientsList.setAdapter(ingredientsListRecyclerView);

        checkAndMaybeShowSaveButton();

        return viewBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkAndMaybeShowSaveButton();
    }

    private void checkAndMaybeShowSaveButton() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(Objects.requireNonNull(getContext()), IngredientsListWidget.class));

        if (appWidgetIds.length > 0) {
            viewBinding.saveToWidget.show();
            viewBinding.saveToWidget.setOnClickListener((view) -> {
                Toast.makeText(getContext(), R.string.saving_to_widget, Toast.LENGTH_SHORT).show();

                IngredientsListWidget.updateAppWidget(Objects.requireNonNull(getContext()),
                        appWidgetManager, appWidgetIds, "Some data " + recipe);
            });
        } else {
            viewBinding.saveToWidget.hide();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        viewModel.getRecipe().removeObservers(this);
    }
}
