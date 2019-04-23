package com.kieranjohnmoore.baking.model;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;

public class Recipe {
    public int id = 0;
    public String name = "";
    public List<Ingredient> ingredients = Collections.emptyList();
    public List<RecipeStep> steps = Collections.emptyList();
    public double servings = 0;
    public String image = "";

    @Override
    @NonNull
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                ", servings=" + servings +
                ", image='" + image + '\'' +
                '}';
    }
}
