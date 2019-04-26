package com.kieranjohnmoore.baking.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kieranjohnmoore.baking.databinding.ItemIngredientBinding;
import com.kieranjohnmoore.baking.model.Ingredient;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class IngredientsListRecyclerView extends RecyclerView.Adapter<IngredientsListRecyclerView.IngredientViewHolder> {
    private static final String TAG = IngredientsListRecyclerView.class.getSimpleName();

    private List<Ingredient> ingredients = Collections.emptyList();

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final ItemIngredientBinding itemBinding =
                ItemIngredientBinding.inflate(layoutInflater, viewGroup, false);
        return new IngredientViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder ingredientViewHolder, int i) {
        ingredientViewHolder.bind(ingredients.get(i));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    void setIngredients(List<Ingredient> ingredients) {
        Log.d(TAG, "Updating ingredients");
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        ItemIngredientBinding binding;
        Ingredient ingredient;

        IngredientViewHolder(ItemIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(@NonNull final Ingredient ingredient) {
            this.ingredient = ingredient;
            binding.setIngredient(ingredient);
            binding.executePendingBindings();
        }
    }
}
