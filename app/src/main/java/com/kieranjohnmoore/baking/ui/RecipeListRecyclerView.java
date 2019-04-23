package com.kieranjohnmoore.baking.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kieranjohnmoore.baking.databinding.ItemRecipeBinding;
import com.kieranjohnmoore.baking.model.Recipe;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeListRecyclerView extends RecyclerView.Adapter<RecipeListRecyclerView.RecipeViewHolder> {
    private static final String TAG = RecipeListRecyclerView.class.getSimpleName();

    private List<Recipe> recipes = Collections.emptyList();

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final ItemRecipeBinding itemBinding =
                ItemRecipeBinding.inflate(layoutInflater, viewGroup, false);
        return new RecipeViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {
        final Recipe recipe = recipes.get(i);
        recipeViewHolder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    void updateRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemRecipeBinding binding;
        Recipe recipe;

        RecipeViewHolder(ItemRecipeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        private void bind(@NonNull final Recipe recipe) {
            this.recipe = recipe;
            binding.setRecipe(recipe);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Clicked: " + recipes.get(getAdapterPosition()).name);
//            final Intent intent = new Intent(v.getContext(), DetailActivity.class);
//            intent.putExtra(MainActivity.RECIPE_ID, recipe.id);
//            v.getContext().startActivity(intent);
        }
    }
}
