package com.kieranjohnmoore.baking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kieranjohnmoore.baking.databinding.ItemStepBinding;
import com.kieranjohnmoore.baking.model.RecipeStep;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

class RecipeStepRecyclerView extends RecyclerView.Adapter<RecipeStepRecyclerView.RecipeViewHolder> {
    private static final String TAG = RecipeStepRecyclerView.class.getSimpleName();

    private List<RecipeStep> steps = Collections.emptyList();

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final ItemStepBinding itemBinding =
                ItemStepBinding.inflate(layoutInflater, viewGroup, false);
        return new RecipeViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {
        final RecipeStep recipe = steps.get(i);
        recipeViewHolder.bind(recipe, i);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    void setSteps(List<RecipeStep> steps) {
        Log.d(TAG, "Updating steps");
        this.steps = steps;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemStepBinding binding;
        RecipeStep recipe;
        int dataLocation;

        RecipeViewHolder(ItemStepBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        private void bind(@NonNull final RecipeStep recipe, int dataLocation) {
            this.recipe = recipe;
            this.dataLocation = dataLocation;
            binding.setRecipe(recipe);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            final int step = getAdapterPosition();
            Log.d(TAG, "Clicked: " + steps.get(step).description);

            final Intent intent = new Intent(ActivityMain.VIEW_STEP);
            intent.putExtra(ActivityMain.DATA_RECIPE_ID, dataLocation);
            intent.putExtra(ActivityMain.DATA_RECIPE_STEP, step);
            LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
        }
    }
}
